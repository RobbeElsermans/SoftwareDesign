package database;

import person.IPerson;

import java.util.Map;

public class PersonDB extends ADatabase<IPerson> {
    private static ADatabase<IPerson> personDB;
    public PersonDB() {
        super();
    }
    public static ADatabase<IPerson> getInstance() {
        if (personDB == null) personDB = new PersonDB();
        return personDB;
    }
}