package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Customer extends User {
    private LocalDate dateOfBirth;

    public Customer() {}

    public Customer(String login, String name, String surname, LocalDate dateOfBirth){
        super(login, name, surname);
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(dateOfBirth, customer.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfBirth);
    }
}
