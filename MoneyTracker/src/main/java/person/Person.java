package person;

public class Person implements IPerson{
    private String name;
    private String surname;
    private int fallGuyId;

    //TODO Gen een ID en validate of dat ze niet bestaat in de database voor alle constructors. Best buiten deze classe halen (Single-responsiblity Principle)
    //De base person methode die iedere constructor moet aanroepen

    //We kunnen een user maken zonder fallguy
    public Person(String name, String surname){
        this.name = name;
        this.surname = surname;
    }
    //We kunnen een user maken met een fallguy
    public Person(String name, String surname, int fallGuyId){
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSurname() {
        return this.surname;
    }

    @Override
    public int getFallguyId() {
        return this.fallGuyId;
    }

    @Override
    public boolean setFallguyId(int id) {
        //TODO: Checken ofdat het id bestaat in de database. Best buiten deze classe halen (Single-responsiblity Principle)
        //this.fallGuyId = id;



        return false;
    }

    @Override
    public String toString() {
        return this.name + " " + this.surname;
    }
}
