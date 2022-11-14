package ticket;

//Strategy Pattern:
//Strategy is a behavioral design pattern that
// lets you define a family of algorithms, put each of
// them into a separate class, and make their objects interchangeable.

public interface ITicket {
    /**
     * Get the ticket ID
     * @return
     */
    int getId();

    /**
     * Get the total price of the ticket
     * @return the total price of the ticket in euro
     */
    double getPrice();

    /**
     * Set the total price of the ticket
     * @param price in euro
     */
    void setPrice(double price);

    /**
     * Get the person ID from the payer
     * @return The persons ID of the payer
     */
    int getPayerId(); //De betaler

    //TODO: getSpenderId() Moet dit geen list zijn van meerdere Persons?

    /**
     * Get the collection of persons wo didn't pay
     * @return ArrayList of persons ID's
     */
    int getSpenderId();  //De mensen die niet hebben betaald

    /**
     * Get the free text
     * @return a text written by the user
     * @description Get the provided text that the user can fill in when he creates a ticket
     */
    String getText();
}
