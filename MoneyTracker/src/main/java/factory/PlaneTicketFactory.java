package factory;

import ticket.ITicket;
import ticket.object.plane.UniPlaneTicket;
import ticket.object.plane.VarPlaneTicket;

import java.util.HashMap;

public class PlaneTicketFactory implements ITicketFactory {
    @Override
    public ITicket getUniformTicket(int payerID, HashMap<Integer, Double> debts) {
        return new UniPlaneTicket(payerID, debts);
    }

    @Override
    public ITicket getVariableTicket(int payerID, HashMap<Integer, Double> debts) {
        return new VarPlaneTicket(payerID, debts);
    }
}
