package daos;

import model.Hall;
import model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private UserDao dao = new UserDao();
    private User user = new User("grzjan","Grzegorz", "Janosz", "haslo");

    @Test
    public void testAddingAndGettingFromDatabase() {
        // given
        // when
        dao.removeUser(user);
        dao.addUser(user);
        User gotUser = dao.getUser(user.getLogin());
        // then
        assertEquals(user.getLogin(), gotUser.getLogin());
        assertEquals(user.getName(), gotUser.getName());
        assertEquals(user.getSurname(), gotUser.getSurname());
        assertTrue(dao.doesUserExist(user.getLogin()));
    }

    @Test
    public void testAddingToDataBase() {
        // given
        int prevSize = dao.getAllUsers().size();
        User newUser = new User("adaber","Adam", "Bera", "haslo2");
        // when
        dao.addUser(newUser);
        // then
        int expected = prevSize + 1;
        assertEquals(expected, dao.getAllUsers().size());
    }

    @Test
    public void testRemovingFromDatabase() {
        // given
        User userToRemove = new User("adaber2","Adam", "Bera", "haslo2");
        dao.addUser(userToRemove);
        // when
        dao.removeUser(userToRemove);
        // then
        assertFalse(dao.doesUserExist(userToRemove.getLogin()));
    }

    @Test
    public void testUpdatingUserName() {
        // given
        String newName = "Andrzej";
        // when
        dao.updateUserName(user.getLogin(), newName);
        // then
        String updatedName = dao.getUser(user.getLogin()).getName();
        assertEquals(newName, updatedName);
    }
}
