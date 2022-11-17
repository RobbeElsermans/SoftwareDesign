package factory;

import ticket.ITicket;

import java.util.HashMap;

public interface ITicketFactory {
    // Equal Division Method
    // Iedereen moet hetzelfde betalen
    // Iedereen krijgt een ticket met hetzelfde bedrag
    ITicket getUniformTicket(int payerID, HashMap<Integer, Double> debts);

    // Separate Division Method
    // Iedereen moet zijn eigen deel betalen
    // Iedereen krijgt een ticket met een eigen bedrag
    ITicket getVariableTicket(int payerID, HashMap<Integer, Double> debts);
}
