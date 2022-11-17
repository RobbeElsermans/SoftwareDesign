package database;

import person.IPerson;

import java.util.HashMap;

public class PersonDB extends ADatabase<IPerson> {

    private static ADatabase<IPerson> personDB;
    private final HashMap<Integer, IPerson> db;

    public PersonDB() {
        this.db = new HashMap<>();
        // this.support = new PropertyChangeSupport(this);
    }

    public static ADatabase<IPerson> getInstance() {
        if (personDB == null) personDB = new PersonDB();
        return personDB;
    }

    @Override
    public void addValue(IPerson value) {
        // TODO: Change after ID maker is created
        int id = (int)(Math.random() * Math.pow(10, 9));
        this.db.put(id, value);
        this.setValue(value);
    }

    @Override
    public IPerson getEntry(IPerson value) {
        return this.db.getOrDefault(value, this.getValue());
    }
}
