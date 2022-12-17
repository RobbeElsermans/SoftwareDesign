package ticket.object.taxi;

import ticket.ATicket;

import java.util.HashMap;

public abstract class TaxiTicket extends ATicket {
    public TaxiTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public TaxiTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
