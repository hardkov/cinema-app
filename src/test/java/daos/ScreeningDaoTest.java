package daos;

import model.Hall;
import model.Movie;
import model.MovieType;
import model.Screening;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningDaoTest {
    private ScreeningDao dao = new ScreeningDao();
    Screening screening = new Screening(
            new Movie("Titanic", LocalDate.of(1992, 02, 02), "komedia"),
            MovieType.MOVIE_2D,
            LocalTime.of(14, 40),
            new Hall(0, 30),
            30,
            14.0f
    );

    @Test
    public void testAddingToDataBase() {
        // given
        int prevSize = dao.getAllScreenings().size();
        // when
        dao.addScreening(screening);
        // then
        int expected = prevSize + 1;
        assertEquals(expected, dao.getAllScreenings().size());
    }
}