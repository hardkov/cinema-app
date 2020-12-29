package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import model.Hall;

import javax.swing.text.Document;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static daos.DaoConstants.hallPath;

public class HallDao {

    Firestore db;
    GenericDao<Hall> hallGenericDao;

    public HallDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
        this.hallGenericDao = new GenericDao<>(Hall.class);
    }

    public boolean addHall(Hall hall) {
        return hallGenericDao.addObject(hallPath, String.valueOf(hall.getHallId()), hall);
    }

    public boolean removeHall(Hall hall) {
        return hallGenericDao.removeObject(hallPath, String.valueOf(hall.getHallId()));
    }

    public boolean doesHallExist(int id) {
        return getHall(id) != null;
    }

    public Hall getHall(int id) {
        return hallGenericDao.getObject(hallPath, String.valueOf(id));
    }

    public DocumentReference getHallReference(Hall hall) {
        return db.collection(hallPath).document(String.valueOf(hall.getHallId()));
    }

    public List<Hall> getAllHalls() {
        return hallGenericDao.getAllObjects(hallPath);
    }

    public boolean updateHall(Hall hall) {
        return hallGenericDao.updateObject(hallPath, String.valueOf(hall.getHallId()), hall);
    }

    public boolean updateHallSeats(int hallID, int newSeatsLimit) {
        String docPath = String.valueOf(hallID);
        return hallGenericDao.updateField(hallPath, docPath, "seatsLimit", newSeatsLimit);
    }
}
