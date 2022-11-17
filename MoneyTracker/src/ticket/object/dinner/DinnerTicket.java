package ticket.object.dinner;

import ticket.ATicket;

import java.util.HashMap;

public abstract class DinnerTicket extends ATicket {
    public DinnerTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public DinnerTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
