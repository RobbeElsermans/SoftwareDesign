package database.dbController;

import database.ADatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                // System.out.printf("ADD - ID: %d, VALUE: %s\n", id, value);
                uploading = false;
            } else {
                // System.out.printf("id %d is in use, create a new id\n", id);
                id = this.createId();
            }
        }
    }

    public HashMap<Integer,T> getAllEntries() { return db.getAll(); }

    public List<Integer> getAllIds(){
        List<Integer> ids = new ArrayList<>();
        for(Map.Entry<Integer, T> entry : db.getAll().entrySet()) {
            int key = entry.getKey();
            ids.add(key);
        }
        return ids;
    }

    public void delValue(int id){ db.delValue(id); }
}
