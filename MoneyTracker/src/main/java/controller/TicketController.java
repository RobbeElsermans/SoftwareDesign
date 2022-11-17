package controller;

import database.ADatabase;
import ticket.ITicket;

public class TicketController extends AController<ITicket>{
    public TicketController(ADatabase<ITicket> db) {
        super(db);
    }
}
