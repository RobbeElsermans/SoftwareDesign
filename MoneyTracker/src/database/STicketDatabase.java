package database;

import ticket.ATicket;
import ticket.ITicket;

import java.util.HashMap;

//Singleton database
public class STicketDatabase {
    //TODO Welk datatypen gaan we gebruiken in de database?
    private static HashMap<Integer, ITicket> database;

    public STicketDatabase(){
        if(database!= null){
            database = new HashMap<>();
        }
    }

    public HashMap<Integer, ITicket> getDatabase(){
        return database;
    }
}
