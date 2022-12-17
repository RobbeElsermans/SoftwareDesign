package ticket.object.plane;
import java.util.HashMap;

public class VarPlaneTicket extends PlaneTicket {
    public VarPlaneTicket(int payerId, HashMap<Integer, Double> debts) {
        super(payerId, debts);
    }

    public VarPlaneTicket(int payerId, HashMap<Integer, Double> debts, String text) {
        super(payerId, debts, text);
    }
}
