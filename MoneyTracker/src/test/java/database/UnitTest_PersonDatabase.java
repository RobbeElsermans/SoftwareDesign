package database;

import database.dbController.PersonController;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import person.IPerson;
import person.Person;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@PrepareForTest(PersonController.class)
public class UnitTest_PersonDatabase {


    @Test
    public void t_add()
    {
        ADatabase<IPerson> personDB = new PersonDB();
        HashMap<Integer, IPerson> actualDatabase = new HashMap<>();

        // Person to be added with id
        int id = 1587165;
        IPerson person1 = new Person("Robbe", "Elsermans");

        // Add to database and local database
        personDB.addValue(id, person1);
        actualDatabase.put(id, person1);

        // Compare
        assertEquals(personDB.getAll(), actualDatabase);
    }

    @Test
    public void t_remove()
    {
        ADatabase<IPerson> personDB = new PersonDB();
        HashMap<Integer, IPerson> actualDatabase = new HashMap<>();

        // Person to be added with id
        int id1 = 1587165;
        IPerson person1 = new Person("Robbe", "Elsermans");

        // Add to database and local database
        personDB.addValue(id1, person1);
        actualDatabase.put(id1, person1);

        // Compare
        assertEquals(personDB.getAll(), actualDatabase);

        // Delete
        personDB.delValue(id1);
        actualDatabase.remove(id1);
        assertEquals(personDB.getAll(), actualDatabase);
    }
}