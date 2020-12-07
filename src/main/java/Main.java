import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customer;
import model.Employee;
import model.Hall;
import model.Permission;
import java.time.LocalDate;
import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {
    public static void main(String[] args){
        Customer customer = new Customer("Jan", "Kowalski", LocalDate.of(1999, 9, 12));
        Permission permissions = Permission.WORKER;
        Employee employee = new Employee("Andrzej","Pracownik", permissions);

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
