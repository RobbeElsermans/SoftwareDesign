package ticket;

public abstract class Ticket {
    protected String id;
    protected double price;
    protected String payerId;
    protected String spenderId;
    protected String text;

    public Ticket(String id, double price, String payerId, String spenderId){
        this.id = id;
        this.price = price;
        this.payerId = payerId;
        this.spenderId = spenderId;
    }

    public Ticket(String id, double price, String payerId, String spenderId, String text){
        this.id = id;
        this.price = price;
        this.payerId = payerId;
        this.spenderId = spenderId;
        this.text = text;
    }

    public String getId() { return id; }
    public double getPrice() { return price; }
    public String getPayerId() { return payerId; }
    public String getSpenderId() { return spenderId; }
    public String getText() { return text; }
}
