package model;

import java.time.LocalDate;
import java.util.Date;

public class Customer extends User {
    private LocalDate dateOfBirth;

    public Customer(String login, String name, String surname, LocalDate dateOfBirth){
        super(login, name, surname);
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
