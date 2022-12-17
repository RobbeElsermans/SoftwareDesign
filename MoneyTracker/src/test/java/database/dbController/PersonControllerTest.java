package database.dbController;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PersonControllerTest {
    /**
     * Method under test: {@link PersonController#getNameById(int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetNameById() {
        // TODO: Complete this test.
        //   Reason: R006 Static initializer failed.
        //   The static initializer of
        //   org.mockito.Mockito
        //   threw java.lang.NoClassDefFoundError while trying to load it.
        //   Make sure the static initializer of Mockito
        //   can be executed without throwing exceptions.
        //   Exception: java.lang.NoClassDefFoundError: Could not initialize class org.mockito.Mockito

        // Arrange
        // TODO: Populate arranged inputs
        PersonController personController = null;
        int id = 0;

        // Act
        String actualNameById = personController.getNameById(id);

        // Assert
        // TODO: Add assertions on result
    }
}

