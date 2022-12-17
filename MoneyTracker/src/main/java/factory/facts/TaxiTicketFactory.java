package factory.facts;

import factory.facts.ITicketFactory;
import ticket.ITicket;
import ticket.object.concert.UniConcertTicket;
import ticket.object.concert.VarConcertTicket;
import ticket.object.taxi.UniTaxiTicket;
import ticket.object.taxi.VarTaxiTicket;

import java.util.HashMap;

public class TaxiTicketFactory implements ITicketFactory {
    @Override
    public ITicket getUniformTicket(int payerID, HashMap<Integer, Double> debts) {
        return new UniTaxiTicket(payerID, debts);
    }

    @Override
    public ITicket getVariableTicket(int payerID, HashMap<Integer, Double> debts) {
        return new VarTaxiTicket(payerID, debts);
    }
}
