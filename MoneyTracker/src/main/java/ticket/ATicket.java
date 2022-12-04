package ticket;

import person.IPerson;

import java.util.HashMap;
import java.util.Map;

public abstract class ATicket implements ITicket {
    // Contains spenderID en debt of spender
    private final HashMap<Integer, Double> debts;
    private int payerId;
    private String text;

    public ATicket(int payerId, HashMap<Integer, Double> debts){
        this.payerId = payerId;
        this.debts = debts;
    }

    public ATicket(int payerId, HashMap<Integer, Double> debts, String text){
        this.payerId = payerId;
        this.text = text;
        this.debts = debts;
    }

    @Override
    public HashMap<Integer, Double> getDebs() { return debts; }

    @Override
    public int getPayerId() { return payerId; }

    @Override
    public String getText() { return text; }
}
