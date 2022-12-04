package ticket;

//Strategy Pattern:
//Strategy is a behavioral design pattern that
// lets you define a family of algorithms, put each of
// them into a separate class, and make their objects interchangeable.

import java.util.HashMap;

public interface ITicket {
    /**
     * Get the debts of a ticket to a specific payer
     * @return The hashmap of the spender id and the amount they owe the payer
     */
    HashMap<Integer, Double> getDebs();

    /**
     * Get the person ID from the payer
     * @return The persons ID of the payer
     */
    int getPayerId(); //De betaler

    /**
     * Get the free text
     * @return a text written by the user
     * @description Get the provided text that the user can fill in when he creates a ticket
     */
    String getText();
}
