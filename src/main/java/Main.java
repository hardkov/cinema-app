import com.google.cloud.firestore.Firestore;
import daos.FirestoreDatabase;
import daos.HallDao;
import daos.UserDao;
import model.*;

import java.net.URISyntaxException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args){
        Customer customer = new Customer("jankow", "Jan", "Kowalski", LocalDate.of(1999, 9, 12));
        Permission permissions = Permission.WORKER;
        Employee employee = new Employee("andpra","Andrzej","Pracownik", permissions);

        System.out.println(customer.getDateOfBirth());
        System.out.println(employee.getPermissions());

        Hall hall = new Hall(0, 20);

        System.out.println(hall.getSeatsLimit());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("addingUser.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
