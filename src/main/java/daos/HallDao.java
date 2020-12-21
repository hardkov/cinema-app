package daos;

import com.google.api.core.ApiFuture;
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

    public void addHall(Hall hall) {
        String docPath = String.valueOf(hall.getHallId());
        ApiFuture<WriteResult> future = db.collection(hallPath).document(docPath).set(hall);
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
}
