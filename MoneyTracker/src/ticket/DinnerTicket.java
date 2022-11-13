package ticket;

public class DinnerTicket extends Ticket{
    public DinnerTicket(String id, double price, String payerId, String spenderId) {
        super(id, price, payerId, spenderId);
    }

    public DinnerTicket(String id, double price, String payerId, String spenderId, String text) {
        super(id, price, payerId, spenderId, text);
    }
}
