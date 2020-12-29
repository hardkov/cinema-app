package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import helpers.DateConverter;
import model.Hall;
import model.Movie;
import model.MovieType;
import model.Screening;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static daos.DaoConstants.moviePath;
import static daos.DaoConstants.screeningPath;

public class ScreeningDao {

    Firestore db;
    GenericDao<Screening> screeningGenericDao;

    private static final String moveField = "movie";
    private static final String typeField = "type";
    private static final String timeField = "time";
    private static final String hallField = "hall";
    private static final String seatsLimitField = "seatsLimit";
    private static final String basePriceField = "basePrice";

    public ScreeningDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
        this.screeningGenericDao = new GenericDao<>(Screening.class);
    }

    public boolean addScreening(Screening screening) {
        Map<String, Object> docData = createScreeningDocData(screening);
        if (docData == null) {
            return false;
        }
        ApiFuture<DocumentReference> writeResult = db.collection(screeningPath).add(docData);
        try {

            screening.setId(writeResult.get().getId());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }

    // method return null if there is no movie or hall from screening
    private Map<String, Object> createScreeningDocData(Screening screening) {
        Map<String, Object> docData = new HashMap<>();
        MovieDao movieDao = new MovieDao();
        Movie movie = screening.getMovie();
        DocumentReference movieReference = movieDao.getMovieReference(movie);
        if (!movieDao.doesMovieExist(movie.getTitle())) {
            return null;
        }
        docData.put(moveField, movieReference);
        docData.put(typeField, screening.getMovieType());
        String timeString = new DateConverter().getTimeString(screening.getTime());
        docData.put(timeField, timeString);
        HallDao hallDao = new HallDao();
        DocumentReference hallReference = hallDao.getHallReference(screening.getHall());
        if (!hallDao.doesHallExist(Integer.parseInt(hallReference.getId()))) {
            return null;
        }
        docData.put(hallField, hallReference);
        docData.put(seatsLimitField, screening.getSeatsLimit());
        docData.put(basePriceField, screening.getBasePrice());
        return docData;
    }

    public DocumentReference getScreeningReference(Screening screening) {
        return db.collection(screeningPath).document(screening.getId());
    }

    public List<Screening> getAllScreenings() {
        ApiFuture<QuerySnapshot> future = db.collection(screeningPath).get();
        List<QueryDocumentSnapshot> documents = null;
        List<Screening> screenings = new ArrayList<>();
        try {
            documents = future.get().getDocuments();
            MovieDao movieDao = new MovieDao();
            HallDao hallDao = new HallDao();
            for (QueryDocumentSnapshot document : documents) {
                DocumentReference movieReference = document.get(moveField, DocumentReference.class);
                Movie movie = movieDao.getMovie(movieReference.getId());
                MovieType movieType = document.get(typeField, MovieType.class);
                String timeString = document.get(timeField, String.class);
                LocalTime time = new DateConverter().getLocalTimeFromString(timeString);
                DocumentReference hallReference = document.get(hallField, DocumentReference.class);
                int hallId = Integer.parseInt(hallReference.getId());
                Hall hall = hallDao.getHall(hallId);
                int seatsLimit = document.get(seatsLimitField, Integer.class);
                float basePrice = document.get(basePriceField, Float.class);
                screenings.add(new Screening(movie, movieType, time, hall, seatsLimit, basePrice));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    public boolean removeScreening(Screening screening) {
        return screeningGenericDao.removeObject(screeningPath, screening.getId());
    }

    public boolean updateScreening(Screening screening) {
        Map<String, Object> docData = createScreeningDocData(screening);
        if (docData == null) {
            return false;
        }
        ApiFuture<WriteResult> writeResult = db.collection(moviePath).document(screening.getId()).set(docData);
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }
}
