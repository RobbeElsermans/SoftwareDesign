package controller;

import database.ADatabase;
import person.IPerson;

import java.util.Map;

public class PersonController extends AController<IPerson>{

    public PersonController(ADatabase<IPerson> db) {
        super(db);
    }

    public int getIdByName(String name, String lastname){
        for(Map.Entry<Integer, IPerson> entry : db.getAll().entrySet()) {
            if (entry.getValue().toString().equals(name + " " + lastname)) return entry.getKey();
            //  Integer key = entry.getKey();
            //  IPerson value = entry.getValue();
        }
        return 0;
    }
}
