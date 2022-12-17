package ticket.object.concert;

import java.util.HashMap;

public class VarConcertTicket extends ConcertTicket {
    public VarConcertTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public VarConcertTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
