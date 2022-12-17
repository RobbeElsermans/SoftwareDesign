package ticket.object.taxi;

import java.util.HashMap;

public class UniTaxiTicket extends TaxiTicket {
    public UniTaxiTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public UniTaxiTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
