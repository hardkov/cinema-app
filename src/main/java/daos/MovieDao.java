package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import helpers.DateConverter;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static daos.DaoConstants.*;
import static daos.DaoConstants.employeePath;

public class MovieDao {

    Firestore db;
    GenericDao<Movie> movieGenericDao;

    private static final String titleField = "title";
    private static final String dateField = "date";
    private static final String genreField = "genre";

    public MovieDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
        this.movieGenericDao = new GenericDao<>(Movie.class);
    }

    public boolean addMovie(Movie movie) {
        return addMovie(movie, true);
    }

    private boolean addMovie(Movie movie, boolean checkIfExists) {
        String title = movie.getTitle();
        if (checkIfExists && doesMovieExist(title)) {
            return false;
        }
        Map<String, Object> docData = new HashMap<>();
        docData.put(titleField, title);
        docData.put(genreField, movie.getGenre());
        String dateString = new DateConverter().getDateString(movie.getDate());
        docData.put(dateField, dateString);
        ApiFuture<WriteResult> writeResult = db.collection(moviePath).document(title).set(docData);
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }

    public boolean removeMovie(Movie movie) {
        return movieGenericDao.removeObject(moviePath, movie.getTitle());
    }

    public boolean doesMovieExist(String title) {
        return getMovie(title) != null;
    }

    public Movie getMovie(String title) {
        DocumentReference docRef = db.collection(moviePath).document(title);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        Movie movie = null;
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                String movieTitle = document.get(titleField, String.class);
                String movieDateString = document.get(dateField, String.class);
                LocalDate moveDate = new DateConverter().getLocalDateFromString(movieDateString);
                String movieGenre = document.get(genreField, String.class);
                movie = new Movie(movieTitle, moveDate, movieGenre);
            }
            else {
                System.out.println(String.format("There is no document %s", docRef.getPath()));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return movie;
    }

    public List<Movie> getAllMovies() {
        ApiFuture<QuerySnapshot> future = db.collection(moviePath).get();
        List<QueryDocumentSnapshot> documents = null;
        List<Movie> movies = new ArrayList<>();
        try {
            documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                String movieTitle = document.get(titleField, String.class);
                String movieDateString = document.get(dateField, String.class);
                LocalDate moveDate = new DateConverter().getLocalDateFromString(movieDateString);
                String movieGenre = document.get(genreField, String.class);
                Movie movie = new Movie(movieTitle, moveDate, movieGenre);
                movies.add(movie);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public boolean updateMovie(Movie movie) {
        return addMovie(movie, false);
    }

    public boolean updateMovieDate(String title, LocalDate newDate) {
        String newDateString = new DateConverter().getDateString(newDate);
        return movieGenericDao.updateField(moviePath, title, dateField, newDateString);
    }

    public boolean updateMovieGenre(String title, String newGenre) {
        return movieGenericDao.updateField(moviePath, title, genreField, newGenre);
    }
}
