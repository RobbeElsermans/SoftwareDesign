package ticket;

public class DinnerTicket extends ATicket {
    public DinnerTicket(int id, double price, int payerId, int spenderId) {
        super(id, price, payerId, spenderId);
    }

    public DinnerTicket(int id, double price, int payerId, int spenderId, String text) {
        super(id, price, payerId, spenderId, text);
    }

}
