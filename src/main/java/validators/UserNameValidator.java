package validators;

import model.User;

import java.util.List;

public class UserNameValidator implements IValidator<User>{
    public boolean isValid(User user, List<String> feedback){
        String name = user.getName();

        if(name.length() < 1){
            if(feedback != null) feedback.add("Name must be at least 1 character long");
            return false;
        }

        return true;
    }
}
