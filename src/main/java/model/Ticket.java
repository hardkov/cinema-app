package model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return Float.compare(ticket.price, price) == 0 &&
                seatId == ticket.seatId &&
                screening.equals(ticket.screening) &&
                customer.equals(ticket.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screening, customer, price, seatId);
    }

    @Override
    public String toString(){
        return String.format("%s, %s, %s, %s, hall: %d, owner: %s, seat: %d", screening.getMovie(), screening.getMovieType(),
                screening.getTime().toString(), screening.getDate().toString(), screening.getHall().getHallId(),
                customer.getLogin(), seatId);
    }
}
