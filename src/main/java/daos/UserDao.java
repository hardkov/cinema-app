package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import model.Customer;
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

    public boolean addUser(User user) {
        String login = user.getLogin();
        if (doesUserExist(login)) {
            return false;
        } else {
            ApiFuture<WriteResult> writeResult = db.collection(userPath).document(login).set(user);
            try {
                writeResult.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return writeResult.isDone();
        }
    }

    public User getUser(String login) {
        DocumentReference docRef = db.collection(userPath).document(login);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        User user = null;
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                user = document.toObject(User.class);
            }
            else {
                System.out.println(String.format("There is no user with login: %s", login));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return user;
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

    public boolean doesUserExist(String login) {
        return getUser(login) != null;
    }

    public boolean removeUser(User user) {
        String login = user.getLogin();
        ApiFuture<WriteResult> writeResult = db.collection(userPath).document(login).delete();
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }

    public boolean updateUser(User user) {
        return addUser(user);
    }

    public boolean updateUserSurname(String login, String newSurname) {
        ApiFuture<WriteResult> writeResult = db.collection(userPath).document(login).update("surname", newSurname);
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }

    public boolean updateUserName(String login, String newName) {
        ApiFuture<WriteResult> writeResult = db.collection(userPath).document(login).update("name", newName);
        try {
            writeResult.get();
            System.out.println("here");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("return");
        return writeResult.isDone();
    }
}
