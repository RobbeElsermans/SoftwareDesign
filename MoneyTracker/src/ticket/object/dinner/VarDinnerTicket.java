package ticket.object.dinner;

import java.util.HashMap;

public class VarDinnerTicket extends DinnerTicket {
    public VarDinnerTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public VarDinnerTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
