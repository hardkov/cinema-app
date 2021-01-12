package Validators;

import com.google.api.UsageRule;
import model.User;

import java.util.List;

public class UserLoginValidator implements IValidator<User> {
    @Override
    public boolean isValid(User user, List<String> feedback) {
        String login = user.getLogin();

        if(login.length() < 1){
            feedback.add("Login must be at least 1 character long");
            return false;
        }

        return true;
    }
}
