package factory;

import factory.facts.*;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class UnitTest_AbstactFactoryProvider {
    @Test
    public void t_CorrectReturn(){
        assertEquals(Objects.requireNonNull(AbstractFactoryProvider.getFactory(FactoryType.DINNER)).getClass(), DinnerTicketFactory.class);
        assertEquals(Objects.requireNonNull(AbstractFactoryProvider.getFactory(FactoryType.CONCERT)).getClass(), ConcertTicketFactory.class);
        assertEquals(Objects.requireNonNull(AbstractFactoryProvider.getFactory(FactoryType.PLANE)).getClass(), PlaneTicketFactory.class);
        assertEquals(Objects.requireNonNull(AbstractFactoryProvider.getFactory(FactoryType.SUPERMARKET)).getClass(), SupermarketTicketFactory.class);
        assertEquals(Objects.requireNonNull(AbstractFactoryProvider.getFactory(FactoryType.TAXI)).getClass(), TaxiTicketFactory.class);
    }
}
