package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.AsyncPageImpl;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import model.Hall;

import java.util.concurrent.ExecutionException;

public class HallDao {

    private static final String hallPath = "halls";
    Firestore db;

    public HallDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
    }

    public boolean addHall(Hall hall) {
        int id = hall.getHallId();
        if (isHall(id)) {
            return false;
        } else {
            String docPath = String.valueOf(id);
            db.collection(hallPath).document(docPath).set(hall);
            return true;
        }
    }

    public boolean removeHall(Hall hall) {
        int id = hall.getHallId();
        String docPath = String.valueOf(id);
        ApiFuture<WriteResult> writeResult = db.collection(hallPath).document(docPath).delete();
        try {
            WriteResult result = writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }

    public boolean isHall(int id) {
        return getHall(id) != null;
    }

    public Hall getHall(int id) {
        String docPath = String.valueOf(id);
        DocumentReference docRef = db.collection(hallPath).document(docPath);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        Hall hall = null;
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                hall = document.toObject(Hall.class);
            }
            else {
                System.out.println(String.format("There is no hall with id: %d", id));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return hall;
    }

    public boolean updateHall(Hall hall) {
        return addHall(hall);
    }

    public boolean updateHallSeats(int hallID, int newSeatsLimit) {
        String docPath = String.valueOf(hallID);
        ApiFuture<WriteResult> writeResult = db.collection(hallPath).document(docPath).update("seatsLimit", newSeatsLimit);
        try {
            WriteResult result = writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }
}
