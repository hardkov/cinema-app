package validators;

import model.Customer;

import java.util.List;

public class CustomerEmailValidator implements IValidator<Customer> {
    @Override
    public boolean isValid(Customer obj, List<String> feedback) {
        if(obj.getEmail().length() < 1){
            if(feedback != null) feedback.add("Email has to be at lest 1 character long");
            return false;
        }
        return true;
    }
}
