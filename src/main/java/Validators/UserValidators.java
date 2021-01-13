package Validators;

import model.User;

import java.util.LinkedList;
import java.util.List;

public class UserValidators implements IValidator<User> {
    public List<IValidator> validators;

    public UserValidators(){
        validators = new LinkedList<IValidator>();
        validators.add(new UserLoginValidator());
        validators.add(new UserNameValidator());
        validators.add(new UserSurnameValidator());
    }

    @Override
    public boolean isValid(User user, List<String> feedback){
        for(IValidator validator : validators){
            if(!validator.isValid(user, feedback)) return false;
        }

        return true;
    }

}
