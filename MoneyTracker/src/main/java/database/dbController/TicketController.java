package database.dbController;

import database.ADatabase;
import person.IPerson;
import ticket.ITicket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketController extends AController<ITicket>{
    public TicketController(ADatabase<ITicket> db) {
        super(db);
    }

    public List<ITicket> getTicketsByPayerId(int payerId){
        List<ITicket> tickets = new ArrayList<>();
        for(Map.Entry<Integer, ITicket> entry : db.getAll().entrySet()) {
            // int key = entry.getKey();
            ITicket value = entry.getValue();
            if (value.getPayerId() == payerId) tickets.add(value);
        }
        return tickets;
    }
}
