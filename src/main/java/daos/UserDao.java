package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import model.Hall;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserDao {
    private static final String userPath = "user";
    Firestore db;

    public UserDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
    }

    public void addUser(User user) {
        ApiFuture<WriteResult> addedDocRef = db.collection(userPath).document(user.getLogin()).set(user);
    }

    public List<User> getAllUsers() {
        ApiFuture<QuerySnapshot> future = db.collection(userPath).get();
        List<QueryDocumentSnapshot> documents = null;
        List<User> users = new ArrayList<>();
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        for (QueryDocumentSnapshot document : documents) {
            users.add(document.toObject(User.class));
        }
        return users;
    }
}
