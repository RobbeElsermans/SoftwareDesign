package person;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class UnitTestPerson {

    @Test
    public void t_constructor_2_params()
    {
        String name = "Robbe";
        String surname = "Robbe";
        String fullName = name + " " + surname;

        IPerson person = new Person(name, surname);

        assertEquals(name, person.getName());
        assertEquals(surname, person.getSurname());
        assertEquals(fullName, person.toString());
    }

    @Test
    public void t_constructor_3_params()
    {
        String name = "Robbe";
        String surname = "Robbe";
        String fullName = name + " " + surname;
        int fallGytId = 80487687;

        IPerson person = new Person(name, surname,fallGytId);

        assertEquals(name, person.getName());
        assertEquals(surname, person.getSurname());
        assertEquals(fullName, person.toString());
        assertEquals(fallGytId, person.getFallguyId());
    }

    @Test
    public void t_fallGuy_add_no_constructor()
    {
        String name = "Robbe";
        String surname = "Robbe";

        int defaultFallGytId = -1;
        int fallGytId = 80487687;

        IPerson person = new Person(name, surname);

        assertEquals(defaultFallGytId, person.getFallguyId());

        person.setFallguyId(fallGytId);
        assertEquals(fallGytId, person.getFallguyId());
    }

    @Test
    public void t_fallGuy_add_with_constructor()
    {
        String name = "Robbe";
        String surname = "Robbe";

        int oldFallGytId = 95841;
        int fallGytId = 80487687;

        IPerson person = new Person(name, surname,oldFallGytId);

        assertEquals(oldFallGytId, person.getFallguyId());

        person.setFallguyId(fallGytId);
        assertEquals(fallGytId, person.getFallguyId());
    }
}
