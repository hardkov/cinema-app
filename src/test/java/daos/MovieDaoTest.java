package daos;

import model.Movie;
import model.MovieGenre;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieDaoTest {
    private MovieDao dao = new MovieDao();
    Movie movie = new Movie("Titanic", LocalDate.of(1998,4,2), MovieGenre.DRAMA);

    @Test
    public void testAddingAndGettingFromDataBase() {
        // given
        // when
        dao.removeMovie(movie);
        dao.addMovie(movie);
        Movie gotMovie = dao.getMovie(movie.getTitle());
        // then
        assertEquals(movie.getTitle(), gotMovie.getTitle());
        assertEquals(movie.getDate(), gotMovie.getDate());
        assertEquals(movie.getDate(), gotMovie.getDate());
        assertTrue(dao.doesMovieExist(movie.getTitle()));
    }

    @Test
    public void testAddingToDataBase() {
        // given
        Movie newMovie = new Movie("Titanic2", LocalDate.of(1998,4,2), MovieGenre.DRAMA);
        dao.removeMovie(newMovie);
        int prevSize = dao.getAllMovies().size();
        // when
        dao.addMovie(newMovie);
        // then
        int expected = prevSize + 1;
        assertEquals(expected, dao.getAllMovies().size());
    }

    @Test
    public void testRemovingFromDatabase() {
        // given
        Movie movieToRemove = new Movie("Titanic3", LocalDate.of(1998,4,2), MovieGenre.DRAMA);
        dao.addMovie(movieToRemove);
        // when
        dao.removeMovie(movieToRemove);
        // then
        assertFalse(dao.doesMovieExist(movieToRemove.getTitle()));
    }

    @Test
    public void testUpdatingMovieGenre() {
        // given
        MovieGenre newGenre = MovieGenre.COMEDY;
        // when
        dao.updateMovieGenre(movie.getTitle(), newGenre);
        // then
        MovieGenre updatedName = dao.getMovie(movie.getTitle()).getGenre();
        assertEquals(newGenre, updatedName);
    }

    @Test
    public void testUpdatingMovieDate() {
        // given
        LocalDate newDate = LocalDate.of(1999, 2,2);
        // when
        dao.updateMovieDate(movie.getTitle(), newDate);
        // then
        LocalDate updatedDate = dao.getMovie(movie.getTitle()).getDate();
        assertEquals(newDate, updatedDate);
    }
}