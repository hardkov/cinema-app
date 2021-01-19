package validators;

import model.Customer;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Calendar;
import java.util.List;

public class CustomerEmailValidator implements IValidator<Customer> {
    @Override
    public boolean isValid(Customer obj, List<String> feedback) {
        if(obj.getEmail().length() < 1){
            if(feedback != null) feedback.add("Email has to be at lest 1 character long");
            return false;
        }

        try{
            InternetAddress emailAddress = new InternetAddress(obj.getEmail());
            emailAddress.validate();
        } catch (AddressException e){
            if(feedback != null) feedback.add("Email is not valid");
            return false;
        }

        return true;
    }
}
