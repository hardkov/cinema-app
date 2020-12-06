import com.google.cloud.firestore.Firestore;
import daos.FirestoreDatabase;
import daos.HallDao;
import daos.UserDao;
import model.*;

import java.net.URISyntaxException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        Customer customer = new Customer("jankow", "Jan", "Kowalski", LocalDate.of(1999, 9, 12));
        Permission permissions = Permission.WORKER;
        Employee employee = new Employee("andpra","Andrzej","Pracownik", permissions);

        System.out.println(customer.getDateOfBirth());
        System.out.println(employee.getPermissions());

        Hall hall = new Hall(0, 20);
        FirestoreDatabase database = new FirestoreDatabase();
        Firestore db = database.getDb();
        HallDao hallDao = new HallDao(db);
        hallDao.addHall(hall);
        Hall gettedHall = hallDao.getHall(0);
        System.out.println(gettedHall.getSeatsLimit());

        UserDao userDao = new UserDao(db);
        User user = new User("grzjan","Grzegorz", "Janosz");
        userDao.addUser(user);
        System.out.println(userDao.getAllUsers().size());
    }
}
