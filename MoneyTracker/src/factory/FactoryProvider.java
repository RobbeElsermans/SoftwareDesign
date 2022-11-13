package factory;

public class FactoryProvider {
    // Zet string om naar een enum
    public static ITicketFactory getFactory(String type)
    {
        switch (type){
            case "Plane":   return new PlaneTicketFactory();
            case "Dinner":  return new DinnerTicketFactory();
            default:    return null;
        }
    }
}
