package model;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Customer extends User {
    private LocalDate dateOfBirth;
    private String email;

    public Customer() {}

    public Customer(String login, String name, String surname, String password, LocalDate dateOfBirth, String email){
        super(login, name, surname, password);
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
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
