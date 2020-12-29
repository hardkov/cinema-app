package daos;

import com.google.cloud.firestore.*;
import model.User;

import java.util.List;

import static daos.DaoConstants.userPath;

public class UserDao {
    Firestore db;
    GenericDao<User> userGenericDao;

    public UserDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
        userGenericDao = new GenericDao<>(User.class);
    }

    public boolean addUser(User user) {
        return userGenericDao.addObject(userPath, user.getLogin(), user);
    }

    public User getUser(String login) {
        return userGenericDao.getObject(userPath, login);
    }

    public List<User> getAllUsers() {
        return userGenericDao.getAllObjects(userPath);
    }

    public boolean doesUserExist(String login) {
        return userGenericDao.doesObjectExist(userPath, login);
    }

    public boolean removeUser(User user) {
        return userGenericDao.removeObject(userPath, user.getLogin());
    }

    public boolean updateUser(User user) {
        return userGenericDao.updateObject(userPath, user.getLogin(), user);
    }

    public boolean updateUserSurname(String login, String newSurname) {
        return userGenericDao.updateField(userPath, login, "surname", newSurname);
    }

    public boolean updateUserName(String login, String newName) {
        return userGenericDao.updateField(userPath, login, "name", newName);
    }
}
