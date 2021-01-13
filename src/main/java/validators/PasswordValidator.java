package validators;

import java.util.List;

public class PasswordValidator implements IValidator<String>{
    public boolean isValid(String password, List<String> feedback){
        if(password.length() < 8){
            if(feedback != null) feedback.add("Password too short, At least 8 characters");
            return false;
        }

        if(password.toLowerCase() == password){
            if(feedback != null) feedback.add("Use at least one capital letter");
            return false;
        }
        if(password.toUpperCase() == password){
            if(feedback != null) feedback.add("Use at least one small letter");
            return false;
        }

        return true;
    }
}
