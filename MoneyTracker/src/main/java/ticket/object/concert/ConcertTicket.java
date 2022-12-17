package ticket.object.concert;

import ticket.ATicket;

import java.util.HashMap;

public abstract class ConcertTicket extends ATicket {
    public ConcertTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public ConcertTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
