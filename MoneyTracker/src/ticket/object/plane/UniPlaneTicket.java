package ticket.object.plane;

import ticket.object.dinner.DinnerTicket;

import java.util.HashMap;

public class UniPlaneTicket extends PlaneTicket {
    public UniPlaneTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public UniPlaneTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
