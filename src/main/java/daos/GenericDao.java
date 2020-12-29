package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import model.User;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static daos.DaoConstants.userPath;

public class GenericDao<T> {
    Firestore db;
    final Class<T> typeParameterClass;

    public GenericDao(Class<T> typeParameterClass) {
        this.db = FirestoreDatabase.getInstance().getDb();
        this.typeParameterClass = typeParameterClass;
    }

    public boolean addObject(String collectionPath, String docPath, T t) {
        if (doesObjectExist(collectionPath, docPath)) {
            return false;
        } else {
            ApiFuture<WriteResult> writeResult = db.collection(collectionPath).document(docPath).set(t);
            try {
                writeResult.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return writeResult.isDone();
        }
    }

    public T getObject(String collectionPath, String docPath) {
        DocumentReference docRef = db.collection(collectionPath).document(docPath);
        return getObject(docRef);
    }

    public T getObject(DocumentReference docRef) {
        ApiFuture<DocumentSnapshot> future = docRef.get();
        T object = null;
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                object = document.toObject(typeParameterClass);
            }
            else {
                System.out.println(String.format("There is no document %s", docRef.getPath()));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return object;
    }

    public List<T> getAllObjects(String collectionPath) {
        ApiFuture<QuerySnapshot> future = db.collection(collectionPath).get();
        List<QueryDocumentSnapshot> documents = null;
        List<T> objects = new ArrayList<>();
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        for (QueryDocumentSnapshot document : documents) {
            objects.add(document.toObject(typeParameterClass));
        }
        return objects;
    }

    public boolean doesObjectExist(String collectionPath, String docPath) {
        return getObject(collectionPath, docPath) != null;
    }

    public boolean removeObject(String collectionPath, String docPath) {
        return removeObject(db.collection(collectionPath).document(docPath));
    }

    public boolean removeObject(DocumentReference docRef) {
        ApiFuture<WriteResult> writeResult = docRef.delete();
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }

    public boolean updateObject(String collectionPath, String docPath, T t) {
        return addObject(collectionPath, docPath, t);
    }

    public boolean updateField(String collectionPath, String docPath, String fieldName, Object newValue) {
        ApiFuture<WriteResult> writeResult = db.collection(collectionPath).document(docPath).update(fieldName, newValue);
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }
}
