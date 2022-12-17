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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest_Tallies {

    @Test
    public void testTwoTallies(){
        //Create 2 persons
        List<IPerson> personList = createPersons(2);

        //Add to database
        PersonController personController = new PersonController(PersonDB.getInstance());
        personList.forEach(personController::addValue);

        //Create tickets
        List<ITicket> ticketList = new ArrayList<>();
        ITicketFactory ticketFactory = new DinnerTicketFactory();
        TicketController ticketController = new TicketController(TicketDB.getInstance());

        //Create depth with dinner and uniform between 0 and 1 for € 20.0
        //0 (A) pays €20 for 1 (B)
        HashMap<Integer, Double> depths = new HashMap<>();
        depths.put(personController.getIdByName(personList.get(1).toString()), 20.0);
        ITicket ticket = ticketFactory.getUniformTicket(personController.getIdByName(personList.get(0).toString()), depths);
        ticketList.add(ticket);

        //save to database

        //Create depth with dinner and uniform between 1 and 0 for € 10.0
        //1 (B) pays €10 for 0 (A)
        depths = new HashMap<>();
        depths.put(personController.getIdByName(personList.get(0).toString()), 10.0);
        ticket = ticketFactory.getUniformTicket(personController.getIdByName(personList.get(1).toString()), depths);
        ticketList.add(ticket);

        //save to the database
        ticketList.forEach(ticketController::addValue);


        //Calculate the tallies. It should be 1 (B) is in depth with 0 (A) with 10.0
        List<Triplet<Integer, Integer, Double>> tallies = Calculator.CalculateFinalTallies(Calculator.CalculateTallyPairs());
        // System.out.println(tallies);
        // Calculator.PrintTallies(tallies);

        assertEquals(tallies.get(0).getValue0(), personController.getIdByName(personList.get(0).toString()));
        assertEquals(tallies.get(0).getValue1(), personController.getIdByName(personList.get(1).toString()));
        assertEquals(tallies.get(0).getValue2(), 10.0);
    }

    @Test
    public void testTreeTallies(){
        //Create 3 persons
        List<IPerson> personList = createPersons(3);

        //Add to database
        PersonController personController = new PersonController(PersonDB.getInstance());
        personList.forEach(personController::addValue);

        //Create tickets
        List<ITicket> ticketList = new ArrayList<>();
        ITicketFactory ticketFactory = new DinnerTicketFactory();
        TicketController ticketController = new TicketController(TicketDB.getInstance());

        //Create depth with dinner and uniform between 0 -> 1 for € 20.0
        //0 (A) pays €20 for 1 (B)
        HashMap<Integer, Double> debts = new HashMap<>();
        debts.put(personController.getIdByName(personList.get(1).toString()), 20.0);
        ITicket ticket = ticketFactory.getUniformTicket(personController.getIdByName(personList.get(0).toString()), debts);
        ticketList.add(ticket);

        //Create depth with dinner and uniform between 1 -> 0 for € 10.0
        //1 (B) pays €10 for 0 (A)
        debts = new HashMap<>();
        debts.put(personController.getIdByName(personList.get(0).toString()), 10.0);
        ticket = ticketFactory.getUniformTicket(personController.getIdByName(personList.get(1).toString()), debts);
        ticketList.add(ticket);

        //Create depth with dinner and uniform between 1 -> 2 for € 30.0
        //1 (B) pays €30 for 2 (C)
        debts = new HashMap<>();
        //TODO als dit verhoogt, dan zal depths onder 0 gaan waardoor er een null pointer komt.
        //Het klopt nog niet helemaal
        debts.put(personController.getIdByName(personList.get(2).toString()), 30.0);
        ticket = ticketFactory.getUniformTicket(personController.getIdByName(personList.get(1).toString()), debts);
        ticketList.add(ticket);

        //save to the database
        ticketList.forEach(ticketController::addValue);


        //Calculate the tallies. It should be 0 -> 1 with 10.0
        List<Triplet<Integer, Integer, Double>> tallies = Calculator.CalculateTallyPairs();
        Calculator.PrintTallies(tallies);
        tallies = Calculator.CalculateFinalTallies(tallies);
        Calculator.PrintTallies(tallies);

        /*assertEquals(tallies.get(0).getValue0(), personController.getIdByName(personList.get(0).toString()));
        assertEquals(tallies.get(0).getValue1(), personController.getIdByName(personList.get(1).toString()));
        assertEquals(tallies.get(0).getValue2(), 10.0);

        //AND 1 -> 2 with 30?
        //TODO Tom kijk dit eens na
        assertEquals(tallies.get(1).getValue0(), personController.getIdByName(personList.get(1).toString()));
        assertEquals(tallies.get(1).getValue1(), personController.getIdByName(personList.get(2).toString()));
        assertEquals(tallies.get(1).getValue2(), 30.0);*/
    }

    private List<IPerson> createPersons(int amount){
        List<IPerson> personsList = new ArrayList<>();

        if (amount > 1) {
            for (int i = 0; i < amount ; i++){
                personsList.add(new Person(String.valueOf((char)(i+65)),String.valueOf((char)(i+65))));
            }
        } else if (amount == 1) {
            personsList.add(new Person("A", "A"));
        } else {
            personsList = null;
        }

        return personsList;
    }
}
