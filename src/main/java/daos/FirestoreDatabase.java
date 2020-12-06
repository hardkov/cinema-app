package daos;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class FirestoreDatabase {

    private static final String projectId = "habeja-to-project";

    private static FirestoreDatabase instance;

    Firestore db;

    private FirestoreDatabase() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/habeja-to-project-firebase-adminsdk-q0xq4-14a5591339.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .setProjectId(projectId)
                    .build();
            FirebaseApp.initializeApp(options);

            this.db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FirestoreDatabase getInstance() {
        if(instance == null) {
            instance = new FirestoreDatabase();
        }
        return instance;
    }

    public Firestore getDb() {
        return db;
    }
}
