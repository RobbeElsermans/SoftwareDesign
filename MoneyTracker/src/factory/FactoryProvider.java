package factory;

public class FactoryProvider {
    public static ITicketFactory getFactory(FactoryType type)
    {
        switch (type){
            case DINNER:    return new DinnerTicketFactory();
            case PLANE:     return new PlaneTicketFactory();
            default:        return null;
        }
    }
}
