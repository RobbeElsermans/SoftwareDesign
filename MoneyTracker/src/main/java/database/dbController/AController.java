package database.dbController;

import database.ADatabase;

public abstract class AController<T> {
    protected ADatabase<T> db;
    public AController(ADatabase<T> db) { this.db = db; }

    private int createId() { return (int)(Math.random() * Math.pow(10, 9)); }

    public void addValue(T value) {
        int id = this.createId();
        boolean uploading = true;
        while (uploading){
            if (this.db.getValue(id) == null){
                this.db.addValue(id, value);
                System.out.printf("ADD - ID: %d, VALUE: %s\n", id, value);
                uploading = false;
            } else {
                // System.out.printf("id %d is in use, create a new id\n", id);
                id = this.createId();
            }
        }
    }

    // TODO Calculate debts between persons --> array

    // TODO Use Splitwise Algorithm / Greedy Algorithm to minimise debs overall
    // https://www.geeksforgeeks.org/minimize-cash-flow-among-given-set-friends-borrowed-money/
}
