import com.google.cloud.firestore.Firestore;
import daos.FirestoreDatabase;
import daos.HallDao;
import daos.UserDao;
import model.*;

import java.net.URISyntaxException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("jankow", "Jan", "Kowalski", LocalDate.of(1999, 9, 12));
        Permission permissions = Permission.WORKER;
        Employee employee = new Employee("andpra","Andrzej","Pracownik", permissions);

        System.out.println(customer.getDateOfBirth());
        System.out.println(employee.getPermissions());
    }
}
