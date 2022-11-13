package ticket;

public class PlaneTicket extends Ticket{
    public PlaneTicket(String id, double price, String payerId, String spenderId) {
        super(id, price, payerId, spenderId);
    }

    public PlaneTicket(String id, double price, String payerId, String spenderId, String text) {
        super(id, price, payerId, spenderId, text);
    }
}
