package ticket.object.concert;

import java.util.HashMap;

public class UniConcertTicket extends ConcertTicket {
    public UniConcertTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public UniConcertTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
