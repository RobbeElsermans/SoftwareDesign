package database;

import person.IPerson;
import ticket.ITicket;

import java.util.HashMap;

public class TickerDB extends ADatabase<ITicket> {

    private static ADatabase<ITicket> ticketDB;
    private final HashMap<Integer, ITicket> db;

    public TickerDB() {
        db = new HashMap<>();
        // this.support = new PropertyChangeSupport(this);
    }

    public static ADatabase<ITicket> getInstance() {
        if (ticketDB == null) ticketDB = new TickerDB();
        return ticketDB;
    }

    @Override
    public void addValue(ITicket value) {
        // TODO: Change after ID maker is created
        int id = (int)(Math.random() * Math.pow(10, 9));
        this.db.put(id, value);
        this.setValue(value);
    }

    @Override
    public ITicket getValue(ITicket value) {
        return null;
    }
}
