package factory;

import factory.facts.*;

public class AbstractFactoryProvider {
    public static ITicketFactory getFactory(FactoryType type)
    {
        switch (type){
            case DINNER:            return new DinnerTicketFactory();
            case PLANE:             return new PlaneTicketFactory();
            case CONCERT:           return new ConcertTicketFactory();
            case SUPERMARKET:       return new SupermarketTicketFactory();
            case TAXI:              return new TaxiTicketFactory();
            default:        return null;
        }
    }
}
