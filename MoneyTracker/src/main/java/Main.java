import database.ADatabase;
import database.PersonDB;
import database.TicketDB;
import factory.FactoryProvider;
import javafx.stage.Stage;
import person.IPerson;
import person.Person;
import ticket.ITicket;
import view.Application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    public void run(){
        System.out.println("Wazaaa");
        FactoryProvider provider = new FactoryProvider();

        // person db test
        PersonDB personDB = (PersonDB) PersonDB.getInstance();
        List<String> names = new ArrayList<String>();
        names.add("Kai");
        names.add("Zion");
        names.add("Jayden");
        names.add("Eliana");
        names.add("Luca");
        names.add("Ezra");
        names.add("Maeve");
        names.add("Aaliyah");
        for (String name: names) {
            personDB.addValue(new Person(name, "Richards"));
        }
        int id = personDB.getIdByName(names.get(3), "Richards");
        System.out.printf("GET - ID: %d\n", id);
        // ticket db test
        ADatabase<ITicket> ticketDB = TicketDB.getInstance();

        //Application app = new Application();
    }
}