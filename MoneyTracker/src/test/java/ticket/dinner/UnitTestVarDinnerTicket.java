package ticket.dinner;

import org.junit.Test;
import ticket.ITicket;
import ticket.object.dinner.VarDinnerTicket;
import ticket.object.plane.UniPlaneTicket;
import ticket.object.plane.VarPlaneTicket;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class UnitTestVarDinnerTicket {
    @Test
    public void t_constructor_var_2_params()
    {
        int payerId = 15678;
        int debtsPersonId1 = 151548;
        double debtsPersonId1Amount = 15.5;

        HashMap<Integer, Double> debts = new HashMap<Integer, Double>();
        debts.put(debtsPersonId1,debtsPersonId1Amount);

        ITicket ticket = new VarDinnerTicket(payerId, debts);

        assertEquals(payerId, ticket.getPayerId());
        assertEquals(debts, ticket.getDebs());
    }

    @Test
    public void t_constructor_var_3_params()
    {
        int payerId = 15678;
        int debtsPersonId1 = 151548;
        double debtsPersonId1Amount = 15.5;
        String text = "Dit is een tekst jonge!";

        HashMap<Integer, Double> debts = new HashMap<Integer, Double>();
        debts.put(debtsPersonId1,debtsPersonId1Amount);

        ITicket ticket = new VarDinnerTicket(payerId, debts, text);

        assertEquals(payerId, ticket.getPayerId());
        assertEquals(debts, ticket.getDebs());
        assertEquals(text, ticket.getText());
    }
}
