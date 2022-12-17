package ticket.object.supermarket;

import java.util.HashMap;

public class VarSupermarketTicket extends SupermarketTicket {
    public VarSupermarketTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public VarSupermarketTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
