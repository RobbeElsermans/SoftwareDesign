package factory.facts;

import factory.facts.ITicketFactory;
import ticket.ITicket;
import ticket.object.concert.ConcertTicket;
import ticket.object.concert.UniConcertTicket;
import ticket.object.concert.VarConcertTicket;

import java.util.HashMap;

public class ConcertTicketFactory implements ITicketFactory {
    @Override
    public ITicket getUniformTicket(int payerID, HashMap<Integer, Double> debts) {
        return new UniConcertTicket(payerID, debts);
    }

    @Override
    public ITicket getVariableTicket(int payerID, HashMap<Integer, Double> debts) {
        return new VarConcertTicket(payerID, debts);
    }
}
