package database;

import person.IPerson;

import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public abstract class ADatabase<T> {

    protected T value;
    protected final HashMap<Integer, T> db;
    protected PropertyChangeSupport support;
    public ADatabase() {
        db = new HashMap<Integer, T>();
    }

    public void setValue(T value) {
        //support.firePropertyChange("RegisterEntry", this.value, value);
        this.value = value;
    }

    protected int createId() { return (int)(Math.random() * Math.pow(10, 9)); }

    public T getValue() { return this.value; }

    public void addValue(T value) {
        int id = this.createId();
        boolean uploading = true;
        while (uploading){
            if (this.db.get(id) == null){
                this.db.put(id, value);
                this.setValue(value);
                System.out.printf("ADD - ID: %d, VALUE: %s\n", id, value);
                uploading = false;
            } else {
                // System.out.printf("id %d is in use, create a new id\n", id);
                id = this.createId();
            }
        }
    }

    public T getValue(int id) { return this.db.getOrDefault(id, this.getValue()); }

    /*public abstract void addValue(T value);
    public abstract T getValue(int id);*/

    /*public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }*/
}
