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
        int id = this.createId();
        boolean uploading = true;
        while (uploading){
            if (this.db.get(id) == null){
                this.db.put(id, value);
                this.setValue(value);
                uploading = false;
            } else {
                id = this.createId();
            }
        }

    }

    @Override
    public IPerson getValue(IPerson value) {
        return this.db.getOrDefault(value, this.getValue());
    }
}
