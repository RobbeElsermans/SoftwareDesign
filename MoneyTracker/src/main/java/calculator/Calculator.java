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
                // Check if the entry owes money to someone else
                if (tallies.stream().filter(tally -> tally.getValue1().equals(entry.getKey())).findFirst().orElse(null) != null){
                    // minEntry == maxEntry crashes the application -> Avoid it!
                    if ((minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) && entry != maxEntry) minEntry = entry;
                    if ((maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) && entry != minEntry) maxEntry = entry;
                }
            }
            if (minEntry != null && maxEntry != null) PassDebts(minEntry.getKey(), maxEntry.getKey(), tallies); // Step 3
            else return tallies;
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
        // Returns a HashMap with a id and all the money they are owed
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

            // Get all debts of a payer
            stream = tallies.stream().filter(tally -> tally.getValue1() == id);

            // Add up all tallies to that payer
            for (Triplet<Integer, Integer, Double> tally: stream.collect(Collectors.toList())) {
                debt -= tally.getValue2();
            }

            amounts.put(id, debt);
        }
        for(Map.Entry<Integer, Double> entry : amounts.entrySet()) {
            // System.out.printf("%s is owed €%.2f\n", pCtrl.getNameById(entry.getKey()), entry.getValue());
        }
        return amounts;
    }

    private static Triplet<Integer, Integer, Double> getTallyFromTallies(int payerId, int spenderId,
                                                                         List<Triplet<Integer, Integer, Double>> tallies){
        return tallies.stream().filter(tally ->
                tally.getValue0().equals(payerId) && tally.getValue1().equals(spenderId)
        ).findFirst().orElse(null);
    };

    private static void PassDebts(int minEntryPayerId, int maxEntryPayerId, List<Triplet<Integer, Integer, Double>> tallies) {
        // Since a tally contains all debts between 2 people:
        // if maxOwesMin exists:    then min = payer & max = spender
        // else:                    then max = payer & min = spender
        int payerId, spenderId;
        if (getTallyFromTallies(minEntryPayerId, maxEntryPayerId, tallies) != null) {
            // ^ Find tallie where minEntryPayerId = payer & where maxEntryPayerId = spenderId
            payerId = minEntryPayerId;
            spenderId = maxEntryPayerId;
        } else {
            payerId = maxEntryPayerId;
            spenderId = minEntryPayerId;
        }

        // Find tally with payer & spender spenderId
        Triplet<Integer, Integer, Double> debtGiver = getTallyFromTallies(payerId, spenderId, tallies);

        // Check tallies where payer owes money
        List<Triplet<Integer, Integer, Double>> toPayList = tallies.stream().filter(tally ->
                tally.getValue1() == payerId).collect(Collectors.toList());
        // PrintTallies(toPayList);

        // Pass the debts to the next person
        // Loop through the entire list until the debt is clear or there are no people to give the debt to
        for (Triplet<Integer, Integer, Double> toPayEntry: toPayList) {
            // Check for an existing tally where the external payer owes the spender
            Triplet<Integer, Integer, Double> debtReceiver = getTallyFromTallies(toPayEntry.getValue0(), spenderId, tallies);
            if (debtReceiver == null) {
                // Check for an existing tally where the spender owes the external payer
                debtReceiver = getTallyFromTallies(toPayEntry.getValue0(), spenderId, tallies);
                if (debtReceiver == null) {
                    // There isn't a relation between spender and external payer, so we'll make one
                    debtReceiver = new Triplet<>(toPayEntry.getValue0(), spenderId, 0.0);
                    tallies.add(debtReceiver);
                }
            }
            int index;
            double externalDebt = toPayEntry.getValue2();   // payer    -> external payer debt
            double internalDebt = debtGiver.getValue2();    // spender  -> payer debt
            double newLinkedDebt;                           // spender  -> external payer debt

            if (externalDebt > internalDebt){
                // The exact debt will be transferred to the new tally
                newLinkedDebt = internalDebt;

                // Update the (payer -> external payer debt)
                index = tallies.indexOf(toPayEntry);
                tallies.set(index, toPayEntry.setAt2(toPayEntry.getValue2() - internalDebt));

                // Remove the (spender -> payer debt)
                tallies.remove(debtGiver);
                break;
            } else if (externalDebt < internalDebt) {
                // Part of the debt will be transferred to the new tally
                newLinkedDebt = externalDebt;

                // Update the (spender -> payer debt)
                index = tallies.indexOf(debtGiver);
                tallies.set(index, debtGiver.setAt2(debtGiver.getValue2() - externalDebt));

                // Remove the (payer -> external payer debt)
                tallies.remove(toPayEntry);
            } else {
                // If external- and internalDebt are the same, then they compensate, and we can remove them both
                // The exact debt will be transferred to the new tally
                newLinkedDebt = internalDebt;

                // Remove both the (spender -> payer debt) & (payer -> external payer debt)
                tallies.remove(toPayEntry);
                tallies.remove(debtGiver);
                break;
            }

            // add the debt to the debt receiver
            index = tallies.indexOf(debtReceiver);
            tallies.set(index, debtReceiver.setAt2(newLinkedDebt));
        }

        while (tallies.stream().filter(tally -> tally.getValue2() == 0.0).findFirst().orElse(null) != null){
            // Remove all tallies with a 0.0 debt
            tallies.remove(tallies.stream().filter(tally2 -> tally2.getValue2() == 0.0).findFirst().orElse(null));
        }

        // PrintTallies(tallies.stream().filter(tally -> tally.getValue0() == minEntryId).collect(Collectors.toList()));
        // PrintTallies(tallies.stream().filter(tally -> tally.getValue0() == maxEntryId).collect(Collectors.toList()));

    }
}
