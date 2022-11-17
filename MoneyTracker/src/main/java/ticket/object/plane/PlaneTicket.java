package ticket.object.plane;

import ticket.ATicket;

import java.util.HashMap;

public abstract class PlaneTicket extends ATicket {
    public PlaneTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public PlaneTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
