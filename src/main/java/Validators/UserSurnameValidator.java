package Validators;

import model.User;

import java.util.List;

public class UserSurnameValidator implements IValidator<User>{
    public boolean isValid(User user, List<String> feedback){
        String surname = user.getSurname();

        if(surname.length() < 1){
            feedback.add("Surname must be at least 1 character long");
            return false;
        }

        return true;
    }
}
