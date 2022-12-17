package database;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import person.IPerson;

class PersonDBTest {
    /**
     * Method under test: {@link PersonDB#getInstance()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetInstance() {
        // TODO: Complete this test.
        //   Reason: R006 Static initializer failed.
        //   The static initializer of
        //   org.mockito.Mockito
        //   threw java.lang.NoClassDefFoundError while trying to load it.
        //   Make sure the static initializer of Mockito
        //   can be executed without throwing exceptions.
        //   Exception: java.lang.NoClassDefFoundError: Could not initialize class org.mockito.Mockito
        //       at java.util.Collections$SingletonList.forEach(Collections.java:4824)

        // Arrange and Act
        // TODO: Populate arranged inputs
        ADatabase<IPerson> actualInstance = PersonDB.getInstance();

        // Assert
        // TODO: Add assertions on result
    }
}

