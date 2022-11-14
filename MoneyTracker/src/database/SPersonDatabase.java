package database;

import person.IPerson;
import ticket.ITicket;

import java.util.HashMap;

//Singleton database
public class SPersonDatabase {
    //TODO Welk datatypen gaan we gebruiken in de database?
    private static HashMap<Integer, IPerson> database;

    public SPersonDatabase(){
        if(database!= null){
            database = new HashMap<>();
        }
    }

    public HashMap<Integer, IPerson> getDatabase(){
        return database;
    }
}
