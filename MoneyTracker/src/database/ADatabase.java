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

    public T getValue() { return this.value; }
    public abstract void addValue(T value);

    /*public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }*/
}
