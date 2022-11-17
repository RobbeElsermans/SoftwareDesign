package ticket.object.dinner;

import java.util.HashMap;

public class UniDinnerTicket extends DinnerTicket {
    public UniDinnerTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public UniDinnerTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
