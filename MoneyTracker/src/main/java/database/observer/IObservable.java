package database.observer;

public interface IObservable {
    void addObserver(IObserver obj);

    void removeObserver(IObserver obj);

    void notifyObserver(String text);
}