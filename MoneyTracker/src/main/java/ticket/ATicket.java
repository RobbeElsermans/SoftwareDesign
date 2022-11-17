package ticket;

import person.IPerson;

import java.util.HashMap;

public abstract class ATicket implements ITicket{
    protected int id;
    // Contains spenderID en debt of spender
    protected final HashMap<Integer, Double> debts;
    protected double price;
    protected int payerId;
    protected int spenderId;
    protected String text;

    public ATicket(int payerId, HashMap<Integer, Double> debts){
        this.payerId = payerId;
        this.debts = debts;
    }

    public ATicket(int payerId, HashMap<Integer, Double> debts, String text){
        this.payerId = payerId;
        this.text = text;
        this.debts = debts;
    }
    public int getPayerId() { return payerId; }
    public String getText() { return text; }

    /*@Override
    public String toString() {
        return this.name + " " + this.surname;
    }*/
}
