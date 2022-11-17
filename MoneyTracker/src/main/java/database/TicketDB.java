package database;

import ticket.ITicket;

import java.util.HashMap;

public class TicketDB extends ADatabase<ITicket> {

    private static ADatabase<ITicket> ticketDB;

    public TicketDB() {
        // this.support = new PropertyChangeSupport(this);
    }

    public static ADatabase<ITicket> getInstance() {
        if (ticketDB == null) ticketDB = new TicketDB();
        return ticketDB;
    }
}
