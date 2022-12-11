package database;

import database.dbController.PersonController;
import org.junit.runner.RunWith;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import person.IPerson;
import person.Person;

//@RunWith(PowerMockRunner.class)
@PrepareForTest(PersonController.class)
public class UTest_PersonController {
    //create a mock db

    @Test
    public void t_add() throws Exception
    {
        // Create mock objects for database and IPerson
        ADatabase mockDb = Mockito.mock(ADatabase.class);
        IPerson p = new Person("robbe", "elsermans");

        PersonController personControllerUnderTest = new PersonController(mockDb);
        personControllerUnderTest.addValue(p);
        Mockito.verify(mockDb, Mockito.times(1));
    }
}