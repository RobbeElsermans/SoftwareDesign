package ticket.object.supermarket;

import java.util.HashMap;

public class UniSupermarketTicket extends SupermarketTicket {
    public UniSupermarketTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public UniSupermarketTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
