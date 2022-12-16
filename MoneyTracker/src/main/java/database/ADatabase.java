package database;

import person.IPerson;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public abstract class ADatabase<T> {
    private T value;
    private final HashMap<Integer, T> db;
    public ADatabase() {
        db = new HashMap<>();
    }

    public void setValue(T value) {
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
    public void delAllValue() {
        this.db.clear();

    }
}
