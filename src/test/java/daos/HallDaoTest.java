package daos;

import com.google.cloud.firestore.Firestore;
import model.Hall;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class HallDaoTest {

    private HallDao dao;

    @BeforeEach
    void setUp() {
        dao = new HallDao();
    }

    @Test
    public void testAddingAndGettingToDatabase() {
        // given
        Hall hall = new Hall(0, 20);
        // when
        dao.addHall(hall);
        // then
        Hall gotHall = dao.getHall(0);
        assertEquals(hall.getHallId(), gotHall.getHallId());
        assertEquals(hall.getSeatsLimit(), gotHall.getSeatsLimit());
    }
}