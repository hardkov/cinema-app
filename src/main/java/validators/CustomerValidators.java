package validators;

import model.Customer;
import model.User;

import java.util.LinkedList;
import java.util.List;

public class CustomerValidators implements IValidator<Customer>{
    public List<IValidator> validators;

    public CustomerValidators(){
        validators = new LinkedList<IValidator>();
        validators.add(new CustomerDateOfBirthValidator());
        validators.add(new CustomerEmailValidator());
        validators.add(new UserValidators());
    }

    @Override
    public boolean isValid(Customer customer, List<String> feedback){
        for(IValidator validator : validators){
            if(!validator.isValid(customer, feedback)) return false;
        }

        return true;
    }
}
