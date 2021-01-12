package Validators;

import java.util.List;
import java.util.Locale;

public class PasswordValidator implements IValidator<String>{
    public boolean isValid(String password, List<String> feedback){
        if(password.length() < 8){
            feedback.add("Password too short, At least 8 characters");
            return false;
        }

        if(password.toLowerCase() == password){
            feedback.add("Use at least one capital letter");
            return false;
        }
        if(password.toUpperCase() == password){
            feedback.add("Use at least one small letter");
            return false;
        }

        return true;
    }
}
