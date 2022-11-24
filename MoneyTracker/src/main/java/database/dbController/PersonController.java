package database.dbController;

import database.ADatabase;
import person.IPerson;

import java.util.Map;

public class PersonController extends AController<IPerson>{

    public PersonController(ADatabase<IPerson> db) {
        super(db);
    }

    public int getIdByName(String name, String lastname){
        for(Map.Entry<Integer, IPerson> entry : db.getAll().entrySet()) {
            int key = entry.getKey();
            IPerson value = entry.getValue();
            if (value.toString().equals(name + " " + lastname)) return key;
        }
        return 0;
    }
}
