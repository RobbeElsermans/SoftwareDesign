package person;

public class Person implements IPerson {
    private String name;
    private String surname;
    private int fallGuyId;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.fallGuyId = -1;
    }

    public Person(String name, String surname, int fallGuyId) {
        this.name = name;
        this.surname = surname;
        this.fallGuyId = fallGuyId;
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
    public boolean setFallguyId(int id) { this.fallGuyId = id;
    return true;}

    @Override
    public String toString() {
        return this.name + " " + this.surname;
    }
}
