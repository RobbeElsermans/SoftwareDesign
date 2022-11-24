package database.dbController;

import database.ADatabase;
import person.IPerson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonController extends AController<IPerson>{

    public PersonController(ADatabase<IPerson> db) {
        super(db);
    }

    public int getIdByName(String fullName){
        for(Map.Entry<Integer, IPerson> entry : db.getAll().entrySet()) {
            int key = entry.getKey();
            IPerson value = entry.getValue();
            if (value.toString().equals(fullName)) return key;
        }
        return 0;
    }

    public List<String> getAllFullNames(){
        List<String> fullNames = new ArrayList<>();
        for(Map.Entry<Integer, IPerson> entry : db.getAll().entrySet()) {
            IPerson value = entry.getValue();
            fullNames.add(value.toString());
        }
        return fullNames;
    }
}
