package database;

import ticket.ITicket;

import java.util.HashMap;

public class TicketDB extends ADatabase<ITicket> {

    private static ADatabase<ITicket> ticketDB;

    public TicketDB() {
        super();
    }

    public static ADatabase<ITicket> getInstance() {
        if (ticketDB == null) ticketDB = new TicketDB();
        return ticketDB;
    }
}
