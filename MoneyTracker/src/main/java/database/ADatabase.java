package database;

import java.beans.PropertyChangeSupport;

public abstract class ADatabase<T> {
    protected T value;
    //protected HashMap<String, T> db;
    protected PropertyChangeSupport support;
    public ADatabase() { }

    public void setValue(T value) {
        support.firePropertyChange("RegisterEntry", this.value, value);
        this.value = value;
    }

    protected int createId() {
        return (int)(Math.random() * Math.pow(10, 9));
    }

    public T getValue() { return this.value; }
    public abstract void addValue(T value);
    public abstract T getValue(T value);

    /*public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }*/
}
