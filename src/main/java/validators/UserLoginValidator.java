package validators;

import daos.UserDao;
import model.User;

import java.util.List;

public class UserLoginValidator implements IValidator<User> {
    UserDao userDao = new UserDao();

    @Override
    public boolean isValid(User user, List<String> feedback) {
        String login = user.getLogin();

        if(login.length() < 1){
            if(feedback != null) feedback.add("Login must be at least 1 character long");
            return false;
        }

        if(userDao.doesUserExist(login)){
            if(feedback != null) feedback.add("There exists user with such login");
            return false;
        }

        return true;
    }
}
