package ticket;

public abstract class ATicket implements ITicket{
    protected int id;
    protected double price;
    protected int payerId;
    protected int spenderId;
    protected String text;

    public ATicket(int id, double price, int payerId, int spenderId){
        this.id = id;
        this.price = price;
        this.payerId = payerId;
        this.spenderId = spenderId;
    }

    public ATicket(int id, double price, int payerId, int spenderId, String text){
        this.id = id;
        this.price = price;
        this.payerId = payerId;
        this.spenderId = spenderId;
        this.text = text;
    }

    public int getId() { return id; }
    public double getPrice() { return price; }
    public void setPrice(double price) {
    }
    public int getPayerId() { return payerId; }
    public int getSpenderId() { return spenderId; }
    public String getText() { return text; }
}
