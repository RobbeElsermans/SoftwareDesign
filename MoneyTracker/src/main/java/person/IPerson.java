package person;

//Strategy Pattern:
//Strategy is a behavioral design pattern that
// lets you define a family of algorithms, put each of
// them into a separate class, and make their objects interchangeable.

public interface IPerson {
    /**
     * Get the persons name
     * @return The name of the person
     */
    String getName();

    /**
     * Get the persons surname
     * @return The surname of the person
     */
    String getSurname();

    /**
     * Get the persons Fullname
     * @return "{name} {surname}"
     */
    String getFullName();

    /**
     * Get the persons ID
     * @return The persons ID
     */
    int getId();

    /**
     * Get the fallguys ID
     * @return The fallguys ID
     */
    int getFallguyId();

    /**
     * Set the fallguys ID
     * @param id
     * @return False if id not found, else true
     * @description Wanneer een fallguy het geld niet meer wilt betalen, dan moet dit naar een andere fallguy.
     * Wanneer men een nieuwe id aanbrengt voor een fallguy, zal deze id in de database gecontroleerd worden of dat ze bestaat.
     * Als het ID niet bestaat, wordt er een false terug gegeven, anders true.
     * Als dit ID hetzelfde is als de persoon, dan is de persoon zelf de fallguy.
     */
    boolean setFallguyId(int id);
}
