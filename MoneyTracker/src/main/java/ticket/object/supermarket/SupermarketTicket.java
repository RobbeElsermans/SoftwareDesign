package ticket.object.supermarket;

import ticket.ATicket;

import java.util.HashMap;

public abstract class SupermarketTicket extends ATicket {
    public SupermarketTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public SupermarketTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
