package database;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public abstract class Database<T> {
    protected T value;
    //protected HashMap<String, T> db;
    protected PropertyChangeSupport support;
    public Database() { }

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
