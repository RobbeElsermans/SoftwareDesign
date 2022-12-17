package factory.facts;

import org.junit.Test;
import ticket.object.concert.UniConcertTicket;
import ticket.object.concert.VarConcertTicket;
import ticket.object.dinner.UniDinnerTicket;
import ticket.object.dinner.VarDinnerTicket;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class UnitTest_ConcertFactory {

    @Test
    public void t_correctReturn_uni_concertFactory(){
        ITicketFactory ticketFactory = new ConcertTicketFactory();

        //predefined items
        int payerId = 15678;
        int debtsPersonId1 = 151548;
        double debtsPersonId1Amount = 15.5;

        HashMap<Integer, Double> debts = new HashMap<Integer, Double>();
        debts.put(debtsPersonId1, debtsPersonId1Amount);

        assertEquals(ticketFactory.getUniformTicket(payerId, debts).getClass(), UniConcertTicket.class);
    }

    @Test
    public void t_correctReturn_var_concertFactory(){
        ITicketFactory ticketFactory = new ConcertTicketFactory();

        //predefined items
        int payerId = 15678;
        int debtsPersonId1 = 151548;
        double debtsPersonId1Amount = 15.5;

        HashMap<Integer, Double> debts = new HashMap<Integer, Double>();
        debts.put(debtsPersonId1, debtsPersonId1Amount);

        assertEquals(ticketFactory.getVariableTicket(payerId, debts).getClass(), VarConcertTicket.class);
    }
}
