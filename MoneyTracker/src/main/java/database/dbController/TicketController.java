package database.dbController;

import database.ADatabase;
import database.TicketDB;
import ticket.ITicket;
import org.javatuples.Triplet;


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
}
