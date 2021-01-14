package daos;

import model.*;
import org.junit.jupiter.api.Test;
import queryBuilders.QueryBuilder;
import queryBuilders.screening.HallScreeningQueryBuilder;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ScreeningDaoTest {
    private ScreeningDao dao = new ScreeningDao();
    Hall hall = new Hall(0, 30);
    Screening screening = new Screening(
            new Movie("Titanic", LocalDate.of(1992, 02, 02), MovieGenre.COMEDY),
            MovieType.MOVIE_2D,
            LocalTime.of(14, 40),
            LocalDate.of(2021, 01, 10),
            hall,
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

    @Test
    public void testHallQueries() {
        // given
        QueryBuilder builder = new HallScreeningQueryBuilder(hall);
        Screening newScreening = new Screening(
                new Movie("Titanic2", LocalDate.of(1992, 02, 02), MovieGenre.COMEDY),
                MovieType.MOVIE_2D,
                LocalTime.of(15, 20),
                LocalDate.of(2021, 01, 11),
                hall,
                30,
                14.0f
        );
        int prevSize = dao.getAllScreeningsWithQuery(builder).size();
        // when
        dao.addScreening(newScreening);
        int afterSize = dao.getAllScreeningsWithQuery(builder).size();
        dao.removeScreening(newScreening);
        // then
        assertEquals( prevSize + 1, afterSize);
    }
}
