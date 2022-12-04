package database;

import person.IPerson;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public abstract class ADatabase<T> {

    protected T value;
    protected final HashMap<Integer, T> db;
    protected PropertyChangeSupport support;
    public ADatabase() {
        db = new HashMap<>();
    }

    public void setValue(T value) {
        //support.firePropertyChange("RegisterEntry", this.value, value);
        this.value = value;
    }

    public T getValue() { return this.value; }

    public void addValue(int id, T value) {
        this.db.put(id, value);
        this.setValue(value);
    }

    public T getValue(int id) { return this.db.get(id); }

    public HashMap<Integer, T> getAll() { return new HashMap<>(db); }

    public void delValue(int id) { this.db.remove(id); }
}
