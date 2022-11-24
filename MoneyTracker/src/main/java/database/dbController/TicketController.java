package database.dbController;

import database.ADatabase;
import database.PersonDB;
import database.TicketDB;
import person.IPerson;
import ticket.ITicket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketController extends AController<ITicket>{
    private static AController<ITicket> ticketCTRL;
    public TicketController(ADatabase<ITicket> db) {
        super(db);
    }

    public static TicketController getInstance() {
        if (ticketCTRL == null) ticketCTRL = new TicketController(TicketDB.getInstance());
        return (TicketController) ticketCTRL;
    }

    public List<ITicket> getTicketsByPayerId(int payerId){
        List<ITicket> tickets = new ArrayList<>();
        for(Map.Entry<Integer, ITicket> entry : db.getAll().entrySet()) {
            ITicket value = entry.getValue();
            if (value.getPayerId() == payerId) tickets.add(value);
        }
        return tickets;
    }

    // TODO Calculate debts between persons --> array


    // TODO Use Splitwise Algorithm / Greedy Algorithm to minimise debs overall
    // https://www.geeksforgeeks.org/minimize-cash-flow-among-given-set-friends-borrowed-money/


}
