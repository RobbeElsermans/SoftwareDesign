package database.dbController;

import database.ADatabase;
import database.TicketDB;
import ticket.ITicket;
import org.javatuples.Triplet;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketController extends AController<ITicket>{
    private static AController<ITicket> ticketCTRL;
    public TicketController(ADatabase<ITicket> db) {
        super(db);
    }

    public static TicketController getInstance() {
        if (ticketCTRL == null) ticketCTRL = new TicketController(TicketDB.getInstance());
        return (TicketController) ticketCTRL;
    }

    public List<ITicket> getTicketsByPayerId(int payerId){
        List<ITicket> tickets = new ArrayList<>();
        for(Map.Entry<Integer, ITicket> entry : db.getAll().entrySet()) {
            ITicket value = entry.getValue();
            if (value.getPayerId() == payerId) tickets.add(value);
        }
        return tickets;
    }

    // TODO Calculate debts between persons --> array
    public void CalculateTallyPairs(){
        List<Integer> personIds = PersonController.getInstance().getAllIds();
        // https://www.geeksforgeeks.org/javatuples-introduction/
        List<Triplet<Integer, Integer, Double>> tallies = new ArrayList<>();

        // Loop through all tickets
        for(Map.Entry<Integer, ITicket> entry : db.getAll().entrySet()) {
            ITicket value = entry.getValue();
            int payer = value.getPayerId();

            // Loop through all debts
            for (Map.Entry<Integer, Double> debt : value.getDebs().entrySet()) {
                int spender = debt.getKey();
                double price = debt.getValue();

                // Check if person paid for themselves
                // If so, go to next entry
                if (!(payer == spender)){
                    // Find tally is in list
                    Triplet<Integer, Integer, Double> tally = tallies.stream().filter(debtItem ->
                            debtItem.getValue0() == payer && debtItem.getValue1() == spender || // Find debt with payer as 1st id and spender as 2nd.
                            debtItem.getValue0() == spender && debtItem.getValue1() == payer    // Find debt with spender as 1st id and payer
                            // as 2nd.
                    ).findFirst().orElse(null);

                    // If tally doesn't exist -> add tally to list
                    // Else change tally if exists
                    if (tally == null) {
                        // value1 (spender) owes value0 (payer) a certain amount of money (value2)
                        tallies.add(new Triplet<>(payer, spender, price));
                        // System.out.printf("New Tally: %s\n", tally);
                    } else {
                        int tallyIndex = tallies.indexOf(tally);
                        double tallyPrice = tally.getValue2();
                        if (tally.getValue0() == payer) tallyPrice += price;    // Add if values match normally
                        if (tally.getValue0() == spender) tallyPrice -= price;  // Subtract if values are reversed
                        tallies.set(tallyIndex, tally.setAt2(tallyPrice));
                        // tally = tally.setAt2(tallyPrice);   // creates new tally with changed price
                        // System.out.printf("Update Tally: %s\n", tally);
                    }
                }
            }
        }
        System.out.printf("Debts: %s\n", tallies);
        System.out.printf("Debts size: %s\n", tallies.size());
    }

    // TODO Use Splitwise Algorithm / Greedy Algorithm to minimise debs overall
    // https://www.geeksforgeeks.org/minimize-cash-flow-among-given-set-friends-borrowed-money/
    public void CalculateGlobalDebts(){

    }

}
