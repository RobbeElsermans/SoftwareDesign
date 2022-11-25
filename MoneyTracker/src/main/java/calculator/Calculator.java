package calculator;

import database.dbController.PersonController;
import database.dbController.TicketController;
import org.javatuples.Triplet;
import ticket.ITicket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calculator {
    // TODO Calculate debts between persons --> array
    public static List<Triplet<Integer, Integer, Double>> CalculateTallyPairs(){
        // https://www.geeksforgeeks.org/javatuples-introduction/
        List<Triplet<Integer, Integer, Double>> tallies = new ArrayList<>();
        TicketController tCtrl = TicketController.getInstance();

        // Loop through all tickets
        for(Map.Entry<Integer, ITicket> entry : tCtrl.getDB().getAll().entrySet()) {
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

        // PrintTallies(tallies);
        // If tally is negative, swap payer and spender
        for (Triplet<Integer, Integer, Double> tally: tallies) {
            if (tally.getValue2() < 0) tallies.set(tallies.indexOf(tally),
                    new Triplet<>(tally.getValue1(), tally.getValue0(), (tally.getValue2()) * -1));
        }
        // PrintTallies(tallies);

        return tallies;
    }

    public static void PrintTallies(List<Triplet<Integer, Integer, Double>> tallies){
        // Print all tallies
        PersonController pCtrl = PersonController.getInstance();
        System.out.printf("Tally-size: %s\n", tallies.size());
        for (Triplet<Integer, Integer, Double> tally: tallies) {
            System.out.printf("%s owes %s €%.2f\n", pCtrl.getNameById(tally.getValue0()),
                    pCtrl.getNameById(tally.getValue1()), tally.getValue2());
        }
    }

    // TODO Use Splitwise Algorithm / Greedy Algorithm to minimise debs overall
    //  Step 1) Check for dual debts (person1 -> person2 -> person3) -> (person1 -> person3 and person2 -> person3)
    //          https://www.geeksforgeeks.org/minimize-cash-flow-among-given-set-friends-borrowed-money/
    //  Step 2) Find the persons who are owed the most and least amount of money
    //  Step 3) Start eliminating debts between the highest and lowest debtors.
    //  Step 4) Restart the process
    public void CalculateFinalTallies(List<Triplet<Integer, Integer, Double>> tallies){
        // Calculate the net amount to be paid to person 'p', and stores it in amount[p].
        PersonController pCtrl = PersonController.getInstance();
        List<Double> amounts = new ArrayList<>();
        for (int id: pCtrl.getAllIds()) {
            // Get all tallies owed to a payer
            Stream<Triplet<Integer, Integer, Double>> stream = tallies.stream().filter(tally -> tally.getValue0() == id);

            // Add up all tallies to that payer
            for (Triplet<Integer, Integer, Double> tally: stream.collect(Collectors.toList())) {

            }
        }
    }
}
