package daos;

import model.Hall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HallDaoTest {

    private int hallId = 0;
    private int seatsLimit = 20;
    private HallDao dao = new HallDao();

    @Test
    public void testAddingAndGettingToDatabase() {
        // given
        Hall hall = new Hall(hallId, seatsLimit);
        // when
        dao.addHall(hall);
        Hall gotHall = dao.getHall(hallId);
        // then
        assertEquals(hall.getHallId(), gotHall.getHallId());
        assertEquals(hall.getSeatsLimit(), gotHall.getSeatsLimit());
        assertTrue(dao.doesHallExist(hall.getHallId()));
    }

    @Test
    public void testRemovingFromDatabase() {
        // given
        Hall hallToRemove = new Hall(1, 50);
        dao.addHall(hallToRemove);
        // when
        dao.removeHall(hallToRemove);
        // then
        assertFalse(dao.doesHallExist(hallToRemove.getHallId()));
    }

    @Test
    public void testUpdatingHallSeats() {
        // given
        int newSeatsLimit = 40;
        // when
        dao.updateHallSeats(hallId, newSeatsLimit);
        int result = dao.getHall(hallId).getSeatsLimit();
        // then
        assertEquals(newSeatsLimit, result);
    }
}
