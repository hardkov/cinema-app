import model.Customer;
import model.Employee;
import model.Hall;
import model.Permission;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args){
        Customer customer = new Customer("Jan", "Kowalski", LocalDate.of(1999, 9, 12));
        Permission permissions = Permission.WORKER;
        Employee employee = new Employee("Andrzej","Pracownik", permissions);

        System.out.println(customer.getDateOfBirth());
        System.out.println(employee.getPermissions());

        Hall hall = new Hall(0, 20);

        System.out.println(hall.getSeatsLimit());
    }
}
