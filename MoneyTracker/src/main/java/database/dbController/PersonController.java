package database.dbController;

import database.ADatabase;
import database.PersonDB;
import person.IPerson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonController extends AController<IPerson> {

    //TODO Maak gewoon de class PersonController static?
    private static AController<IPerson> personCTRL;

    public PersonController(ADatabase<IPerson> db) {
        super(db);
    }

    public static PersonController getInstance() {
        if (personCTRL == null) personCTRL = new PersonController(PersonDB.getInstance());
        return (PersonController) personCTRL;
    }

    public String getNameById(int id){ return db.getValue(id).getName(); }

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

    public List<Integer> getAllIds(){
        List<Integer> ids = new ArrayList<>();
        for(Map.Entry<Integer, IPerson> entry : db.getAll().entrySet()) {
            int key = entry.getKey();
            ids.add(key);
        }
        return ids;
    }
}
