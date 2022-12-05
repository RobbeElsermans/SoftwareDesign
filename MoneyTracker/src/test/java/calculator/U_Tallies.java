package calculator;

import database.PersonDB;
import database.TicketDB;
import database.dbController.PersonController;
import database.dbController.TicketController;
import factory.facts.DinnerTicketFactory;
import factory.facts.ITicketFactory;
import org.javatuples.Triplet;
import org.junit.jupiter.api.Test;
import person.IPerson;
import person.Person;
import ticket.ITicket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class U_Tallies {

    @Test
    public void testTwoTallies(){
        //Create 2 persons
        List<IPerson> personList = createPersons(2);

        //Add to database
        PersonController personController = new PersonController(PersonDB.getInstance());
        personList.forEach(value -> {
            personController.addValue(value);
        });


        //Create tickets
        List<ITicket> ticketList = new ArrayList<>();
        ITicketFactory ticketFactory = new DinnerTicketFactory();
        //Add to database
        TicketController ticketController = new TicketController(TicketDB.getInstance());

        //Create depth with dinner and unifrom between 0 and 1 for € 20.0
        ticketFactory = new DinnerTicketFactory();
        HashMap<Integer, Double> hashMap = new HashMap<>();
        hashMap.put(personController.getIdByName(personList.get(1).toString()), 20.0);
        ticketList.add(ticketFactory.getUniformTicket(personController.getIdByName(personList.get(0).toString()), hashMap));

        //save to database

        //Create depth with dinner and unifrom between 1 and 0 for € 10.0
        hashMap = new HashMap<>();
        hashMap.put(personController.getIdByName(personList.get(0).toString()), 10.0);
        ticketList.add(ticketFactory.getUniformTicket(personController.getIdByName(personList.get(1).toString()), hashMap));

        //save to the database
        ticketList.forEach(ticketController::addValue);

        //Calculate the tallies. It should be 1 is in depth with 0 with 10.0
        List<Triplet<Integer, Integer, Double>> tallies = Calculator.CalculateFinalTallies(Calculator.CalculateTallyPairs());
        //System.out.println(tallies);

        assertEquals(tallies.get(0).getValue0(), personController.getIdByName(personList.get(0).toString()));
        assertEquals(tallies.get(0).getValue1(), personController.getIdByName(personList.get(1).toString()));
        assertEquals(tallies.get(0).getValue2(), 10.0);
    }

    private List<IPerson> createPersons(int amount){
        List<IPerson> personsList = new ArrayList<>();

        if (amount > 1)
        for (int i = 0; i < amount ; i++){
            personsList.add(new Person(String.valueOf((char)(i+65)),String.valueOf((char)(i+65))));
        }
        else if(amount == 1) {
            personsList.add(new Person("A", "A"));
        }
        else
        {
            personsList = null;
        }

        return personsList;
    }
}
