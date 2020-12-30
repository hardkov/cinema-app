package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import queryBuilders.QueryBuilder;
import helpers.DateConverter;
import model.Hall;
import model.Movie;
import model.MovieType;
import model.Screening;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static daos.DaoConstants.screeningPath;

public class ScreeningDao {

    Firestore db;
    GenericDao<Screening> screeningGenericDao;

    public static final String moveField = "movie";
    public static final String typeField = "type";
    public static final String timeField = "time";
    public static final String hallField = "hall";
    public static final String seatsLimitField = "seatsLimit";
    public static final String basePriceField = "basePrice";

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

    public Screening getScreeningWithScreeningId(String screeningId) {
        DocumentReference docRef = db.collection(screeningPath).document(screeningId);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        Screening screening = null;
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                screening = getScreeningFromDocumentSnapshot(document);
            }
            else {
                System.out.println(String.format("There is no document %s", docRef.getPath()));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return screening;
    }

    public List<Screening> getAllScreenings() {
        ApiFuture<QuerySnapshot> future = db.collection(screeningPath).get();
        List<QueryDocumentSnapshot> documents = null;
        List<Screening> screenings = new ArrayList<>();
        try {
            documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                screenings.add(getScreeningFromDocumentSnapshot(document));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    public List<Screening> getAllScreeningsWithQuery(QueryBuilder builder) {
        CollectionReference collectionReference = db.collection(screeningPath);
        Query query = builder.build(collectionReference);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<Screening> screenings = new ArrayList<>();
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                screenings.add(getScreeningFromDocumentSnapshot(document));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return screenings;
    }

    private Screening getScreeningFromDocumentSnapshot(DocumentSnapshot document) {
        MovieDao movieDao = new MovieDao();
        HallDao hallDao = new HallDao();
        String id = document.getId();
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
        return new Screening(id, movie, movieType, time, hall, seatsLimit, basePrice);
    }

    public boolean removeScreening(Screening screening) {
        if (screening.getId() == null) {
            System.out.println("Screening has no id");
            return false;
        }
        return screeningGenericDao.removeObject(screeningPath, screening.getId());
    }

    public boolean updateScreening(Screening screening) {
        Map<String, Object> docData = createScreeningDocData(screening);
        if (docData == null) {
            return false;
        }
        ApiFuture<WriteResult> writeResult = db.collection(screeningPath).document(screening.getId()).set(docData);
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }
}
