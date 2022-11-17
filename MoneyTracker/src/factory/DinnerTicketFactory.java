package factory;

import ticket.ITicket;
import ticket.object.dinner.UniDinnerTicket;
import ticket.object.dinner.VarDinnerTicket;

import java.util.HashMap;

public class DinnerTicketFactory implements ITicketFactory {
    @Override
    public ITicket getUniformTicket(int payerID, HashMap<Integer, Double> debts) {
        return new UniDinnerTicket(payerID, debts);
    }

    @Override
    public ITicket getVariableTicket(int payerID, HashMap<Integer, Double> debts) {
        return new VarDinnerTicket(payerID, debts);
    }
}
