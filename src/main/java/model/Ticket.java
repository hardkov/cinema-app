package model;

public class Ticket {
    private String id;
    private Screening screening;
    private Customer customer;
    private float price;
    private int seatId;

    public Ticket() {
    }

    public Ticket(Screening screening, Customer customer, float price, int seatId) {
        this.screening = screening;
        this.customer = customer;
        this.price = price;
        this.seatId = seatId;
    }

    public Screening getScreening() {
        return screening;
    }

    public Customer getCustomer() {
        return customer;
    }

    public float getPrice() {
        return price;
    }

    public int getSeatId() {
        return seatId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
