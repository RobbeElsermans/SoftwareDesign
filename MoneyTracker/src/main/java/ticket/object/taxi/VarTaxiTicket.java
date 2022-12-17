package ticket.object.taxi;

import java.util.HashMap;

public class VarTaxiTicket extends TaxiTicket {
    public VarTaxiTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public VarTaxiTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
