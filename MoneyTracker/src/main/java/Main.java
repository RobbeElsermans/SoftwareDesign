import GUI.App;
import calculator.Calculator;
import database.dbController.PersonController;
import database.dbController.TicketController;
import factory.AbstractFactoryProvider;
import factory.FactoryType;
import factory.facts.ITicketFactory;
import org.javatuples.Triplet;
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
        // App.App();
    }
    public void run() {
        // person db test
        PersonController pController = PersonController.getInstance();
        List<String> names = new ArrayList<String>();
        names.add("Kai");
        names.add("Zion");
        names.add("Jayden");
        names.add("Ezra");
        names.add("Maeve");
        names.add("Aaliyah");
        for (String name: names) {
            pController.addValue(new Person(name, "Richards"));
        }
        // ticket db test
        ITicketFactory factory = AbstractFactoryProvider.getFactory(FactoryType.PLANE);
        TicketController tController = TicketController.getInstance();

        for (int i = 0; i < names.size(); i++) {
            int payerId = pController.getIdByName(names.get(i) +  " Richards");
            for (String name: names) {
                int spenderId = pController.getIdByName(name + " Richards");
                double price = Math.round((37.0 / (i + 1)) * 100.0) / 100.0;
                ITicket ticket = factory.getUniformTicket(payerId, new HashMap<Integer, Double>(){{ put(spenderId, price); }});
                tController.addValue(ticket);
            }
        }

        // tController.distributeDebts(pController.getIdByName("Kai Richards"), pController.getIdByName("Aaliyah Richards"));

        List<Triplet<Integer, Integer, Double>> tallies = Calculator.CalculateTallyPairs();
        Calculator.CalculateFinalTallies(tallies);
    }
}