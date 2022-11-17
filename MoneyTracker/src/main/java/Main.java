import controller.PersonController;
import controller.TicketController;
import database.ADatabase;
import database.PersonDB;
import database.TicketDB;
import factory.FactoryProvider;
import factory.FactoryType;
import factory.ITicketFactory;
import javafx.stage.Stage;
import person.IPerson;
import person.Person;
import ticket.ITicket;
import ticket.TicketType;
import ticket.object.plane.PlaneTicket;
import view.Application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    public void run(){
        System.out.println("Wazaaa");

        // person db test
        PersonController pController = new PersonController(PersonDB.getInstance());
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
            pController.addValue(new Person(name, "Richards"));
        }
        // ticket db test
        ITicketFactory factory = FactoryProvider.getFactory(FactoryType.PLANE);
        TicketController tController = new TicketController(TicketDB.getInstance());
        int payerId = pController.getIdByName(names.get(3), "Richards");
        HashMap<Integer, Double> debts = new HashMap<>();
        for (String name: names) {
            int spenderId = pController.getIdByName(name, "Richards");
            ITicket ticket = factory.getUniformTicket(payerId, new HashMap<Integer, Double>(){{ put(spenderId, 10.0); }});
            tController.addValue(ticket);
        }
        //System.out.printf("GET - ID: %d\n", id);

        //Application app = new Application();
    }
}