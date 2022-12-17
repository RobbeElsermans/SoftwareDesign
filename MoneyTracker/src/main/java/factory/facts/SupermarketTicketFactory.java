package factory.facts;

import factory.facts.ITicketFactory;
import ticket.ITicket;
import ticket.object.concert.UniConcertTicket;
import ticket.object.concert.VarConcertTicket;
import ticket.object.supermarket.UniSupermarketTicket;
import ticket.object.supermarket.VarSupermarketTicket;

import java.util.HashMap;

public class SupermarketTicketFactory implements ITicketFactory {
    @Override
    public ITicket getUniformTicket(int payerID, HashMap<Integer, Double> debts) {
        return new UniSupermarketTicket(payerID, debts);
    }

    @Override
    public ITicket getVariableTicket(int payerID, HashMap<Integer, Double> debts) {
        return new VarSupermarketTicket(payerID, debts);
    }
}
