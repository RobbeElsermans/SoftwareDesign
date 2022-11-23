package factory;

import factory.facts.DinnerTicketFactory;
import factory.facts.ITicketFactory;
import factory.facts.PlaneTicketFactory;

public class AbstractFactoryProvider {
    public static ITicketFactory getFactory(FactoryType type)
    {
        switch (type){
            case DINNER:    return new DinnerTicketFactory();
            case PLANE:     return new PlaneTicketFactory();
            default:        return null;
        }
    }
}
