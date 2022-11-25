import GUI.App;
import calculator.Calculator;
import database.dbController.PersonController;
import database.dbController.TicketController;
import database.PersonDB;
import database.TicketDB;
import factory.AbstractFactoryProvider;
import factory.FactoryType;
import factory.facts.ITicketFactory;
import person.Person;
import ticket.ITicket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main{
    public static void main(String[] args)  {
        Main main = new Main();
        main.run();

        //Applicatie stuff
        //App.App();
    }
    public void run() {
        System.out.println("Wazaaa");

        // person db test
        PersonController pController = PersonController.getInstance();
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
        ITicketFactory factory = AbstractFactoryProvider.getFactory(FactoryType.PLANE);
        TicketController tController = TicketController.getInstance();
        for (int i = 1; i <= names.size(); i++) {
            int payerId = pController.getIdByName(names.get(i-1) +  " Richards");
            HashMap<Integer, Double> debts = new HashMap<>();
            for (String name: names) {
                int spenderId = pController.getIdByName(name + " Richards");
                double price = Math.round((37.0 / i) * 100.0) / 100.0;
                ITicket ticket = factory.getUniformTicket(payerId, new HashMap<Integer, Double>(){{ put(spenderId, price); }});
                tController.addValue(ticket);
            }
        }
        Calculator.CalculateTallyPairs();
    }
}