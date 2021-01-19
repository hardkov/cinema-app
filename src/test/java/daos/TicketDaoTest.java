package daos;

import model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TicketDaoTest {
    private TicketDao dao = new TicketDao();

    Ticket ticket = new Ticket(
            new Screening(
                    "JKdJNvFw3LHaISMjJNBE",
                new Movie("Titanic", LocalDate.of(1992, 02, 02), MovieGenre.COMEDY, 90),
                MovieType.MOVIE_2D,
                LocalTime.of(14, 40),
                LocalDate.of(2021, 01, 10),
                new Hall(0, 30),
                30,
                14.0f
            ),
            new Customer("gregory", "Ada", "Bera", "trudne", LocalDate.of(1998, 3, 20), "adaber@gmail.com"),
            14.0f,
            15
    );

    @Test
    public void testAddingToDataBase() {
        // given
        int prevSize = dao.getAllTickets().size();
        // when
        dao.addTicket(ticket);
        // then
        int expected = prevSize + 1;
        assertEquals(expected, dao.getAllTickets().size());
    }
}
