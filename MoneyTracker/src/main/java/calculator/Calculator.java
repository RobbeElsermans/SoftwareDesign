package calculator;

import database.dbController.PersonController;
import database.dbController.TicketController;
import org.javatuples.Triplet;
import ticket.ITicket;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Calculator {
    // Calculate debts between persons --> array
    public static List<Triplet<Integer, Integer, Double>> CalculateTallyPairs(){
        // https://www.geeksforgeeks.org/javatuples-introduction/
        List<Triplet<Integer, Integer, Double>> tallies = new ArrayList<>();
        TicketController tCtrl = TicketController.getInstance();

        // Loop through all tickets
        for(Map.Entry<Integer, ITicket> entry : tCtrl.getAllEntries().entrySet()) {
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
        ReverseNegativeTallies(tallies);
        // PrintTallies(tallies);
        return tallies;
    }

    public static List<String> TalliesToString(List<Triplet<Integer, Integer, Double>> tallies){
        // Print all tallies
        PersonController pCtrl = PersonController.getInstance();
        List<String> output = new ArrayList<>();
        for (Triplet<Integer, Integer, Double> tally: tallies) {
            output.add(String.format("%s owes %s €%.2f\n", pCtrl.getNameById(tally.getValue1()),
                    pCtrl.getNameById(tally.getValue0()), tally.getValue2()));
        }
        return output;
    }

    public static void PrintTallies(List<Triplet<Integer, Integer, Double>> tallies){
        // Print all tallies
        System.out.printf("\nTally-size: %s\n", tallies.size());
        for (String tally: TalliesToString(tallies)) {
            System.out.printf(tally);
        }
    }

    private static void ReverseNegativeTallies(List<Triplet<Integer, Integer, Double>> tallies){
        // PrintTallies(tallies);
        // If tally is negative, swap payer and spender
        for (Triplet<Integer, Integer, Double> tally: tallies) {
            if (tally.getValue2() < 0) tallies.set(tallies.indexOf(tally),
                    new Triplet<>(tally.getValue1(), tally.getValue0(), (tally.getValue2()) * -1));
        }
        // PrintTallies(tallies);
    }

    // Use Splitwise Algorithm / Greedy Algorithm to minimise debs overall
    // Step 1) Check for dual step debts (person1 -> person2 -> person3) -> (person1 -> person3 and person2 -> person3)
    //         https://www.geeksforgeeks.org/minimize-cash-flow-among-given-set-friends-borrowed-money/
    // Step 2) Find the persons who are owed the most and least amount of money
    // Step 3) Start eliminating debts between the highest and lowest debtors.
    // Step 4) Restart the process
    public static List<Triplet<Integer, Integer, Double>> CalculateFinalTallies(List<Triplet<Integer, Integer, Double>> tallies) {
        System.out.print("\n");
        if (CheckForDualStepDebts(tallies)) {   // Step 1
            // https://stackoverflow.com/a/5911199
            Map.Entry<Integer, Double> minEntry = null, maxEntry = null;
            for (Map.Entry<Integer, Double> entry : GetTotalDebtToPerson(tallies).entrySet()) { // Step 2
                if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) minEntry = entry;
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) maxEntry = entry;
            }
            PassDebts(minEntry.getKey(), maxEntry.getKey(), tallies);// Step 3
            ReverseNegativeTallies(tallies);
            tallies = CalculateFinalTallies(tallies);   // Step 4
        }
        return tallies;
    }

    private static boolean CheckForDualStepDebts(List<Triplet<Integer, Integer, Double>> tallies){
        for (Triplet<Integer, Integer, Double> tally: tallies) {
            int spenderId = tally.getValue1();
            // Find a tallies where ones spender matches the others payer
            Triplet<Integer, Integer, Double> dualPath = tallies.stream().filter(debt ->
                    debt.getValue0() == spenderId).findFirst().orElse(null);
            if (dualPath != null) return true;
        }
        return false;
    }

    private static HashMap<Integer, Double> GetTotalDebtToPerson(List<Triplet<Integer, Integer, Double>> tallies) {
        // Returns a HashMap with a payer id and all the money they are owed
        HashMap<Integer, Double> amounts = new HashMap<>();
        PersonController pCtrl = PersonController.getInstance();
        for (int id: pCtrl.getAllIds()) {
            double debt = 0.0;
            // Get all tallies owed to a payer
            Stream<Triplet<Integer, Integer, Double>> stream = tallies.stream().filter(tally -> tally.getValue0() == id);

            // Add up all tallies to that payer
            for (Triplet<Integer, Integer, Double> tally: stream.collect(Collectors.toList())) {
                debt += tally.getValue2();
            }

            // Only store people who are owed money
            if (debt != 0.0) { amounts.put(id, debt); }
        }
        for(Map.Entry<Integer, Double> entry : amounts.entrySet()) {
            // System.out.printf("%s is owed €%.2f\n", pCtrl.getNameById(entry.getKey()), entry.getValue());
        }
        return amounts;
    }

    private static void PassDebts(int minEntryId, int maxEntryId, List<Triplet<Integer, Integer, Double>> tallies) {
        // Check min and max debts
        List<Triplet<Integer, Integer, Double>> minList = tallies.stream().filter(tally -> tally.getValue0() == minEntryId).collect(Collectors.toList());
        // PrintTallies(minList);
        PrintTallies(tallies.stream().filter(tally -> tally.getValue0() == maxEntryId).collect(Collectors.toList()));

        // Pass the debts to the next person
        for (Triplet<Integer, Integer, Double> minEntry: minList) {
            // Remove the debt from the debt passer
            Triplet<Integer, Integer, Double> debtPasser = tallies.stream().filter(tally -> tally.getValue0().equals(maxEntryId)
                    && tally.getValue1().equals(minEntryId)).findFirst().orElse(null);
            if (debtPasser != null) tallies.set(tallies.indexOf(debtPasser), debtPasser.setAt2((debtPasser.getValue2() - minEntry.getValue2())));

            // Add the debt to the debt receiver
            Triplet<Integer, Integer, Double> debtReceiver = tallies.stream().filter(tally -> tally.getValue0().equals(maxEntryId)
                    && tally.getValue1().equals(minEntry.getValue1())).findFirst().orElse(null);
            if (debtReceiver != null) tallies.set(tallies.indexOf(debtReceiver), debtReceiver.setAt2((debtReceiver.getValue2() + minEntry.getValue2())));

            // Remove the dual step debt
            tallies.remove(minEntry);
        }

        // PrintTallies(tallies.stream().filter(tally -> tally.getValue0() == minEntryId).collect(Collectors.toList()));
        // PrintTallies(tallies.stream().filter(tally -> tally.getValue0() == maxEntryId).collect(Collectors.toList()));

    }
}
