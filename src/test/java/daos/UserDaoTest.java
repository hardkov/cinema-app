package daos;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private UserDao dao;

    @BeforeEach
    void setUp() {
        dao = new UserDao();
    }

    @Test
    public void testAddingToDataBase() {
        // given
        User user = new User("grzjan","Grzegorz", "Janosz");
        // when
        dao.addUser(user);
        // then
        assertEquals(1, dao.getAllUsers().size());
    }

}