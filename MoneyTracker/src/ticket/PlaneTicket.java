package ticket;

public class PlaneTicket extends ATicket {
    public PlaneTicket(int id, double price, int payerId, int spenderId) {
        super(id, price, payerId, spenderId);
    }

    public PlaneTicket(int id, double price, int payerId, int spenderId, String text) {
        super(id, price, payerId, spenderId, text);
    }
}
