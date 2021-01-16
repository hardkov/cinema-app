package validators;

import model.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomerDateOfBirthValidator implements IValidator<Customer> {
    @Override
    public boolean isValid(Customer obj, List<String> feedback) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try{
            obj.getDateOfBirth().format(formatter);
        } catch (NullPointerException e){
            if(feedback != null) feedback.add("Invalid date format");
            return false;
        }

        if(obj.getDateOfBirth().isBefore(LocalDate.of(1890, 1, 1))){
            if(feedback != null) feedback.add("Date is too early for a human");
            return false;
        }

        if(obj.getDateOfBirth().isAfter(LocalDate.now())){
            if(feedback != null) feedback.add("You need to be born in the past");
            return false;
        }


        return true;

    }
}
