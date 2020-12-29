package daos;

import model.Customer;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoTest {
    private CustomerDao dao= new CustomerDao();
    private Customer customer = new Customer("krzhar", "Krzysztof", "Hardek", LocalDate.of(1998,4,22));

    @Test
    public void testAddingAndGettingFromDataBase() {
        // given
        // when
        dao.removeCustomer(customer);
        dao.addCustomer(customer);
        Customer gotCustomer = dao.getCustomer(customer.getLogin());
        // then
        assertEquals(customer.getLogin(), gotCustomer.getLogin());
        assertEquals(customer.getName(), gotCustomer.getName());
        assertEquals(customer.getSurname(), gotCustomer.getSurname());
        assertEquals(customer.getDateOfBirth(), gotCustomer.getDateOfBirth());
        assertTrue(dao.doesCustomerExist(customer.getLogin()));
    }

    @Test
    public void testAddingToDataBase() {
        // given
        int prevSize = dao.getAllCustomers().size();
        Customer newCustomer = new Customer("addaac","Ada", "Bera", LocalDate.of(1998, 3, 20));
        // when
        dao.addCustomer(newCustomer);
        // then
        int expected = prevSize + 1;
        assertEquals(expected, dao.getAllCustomers().size());
    }

    @Test
    public void testRemovingFromDatabase() {
        // given
        Customer customerToRemove = new Customer("toRemove","Ada", "Bera", LocalDate.of(1998, 3, 20));
        dao.addCustomer(customerToRemove);
        // when
        dao.removeCustomer(customerToRemove);
        // then
        assertFalse(dao.doesCustomerExist(customerToRemove.getLogin()));
    }

    @Test
    public void testUpdatingCustomerName() {
        // given
        String newName = "Szymon";
        // when
        dao.updateCustomerName(customer.getLogin(), newName);
        // then
        String updatedName = dao.getCustomer(customer.getLogin()).getName();
        assertEquals(newName, updatedName);
    }

    @Test
    public void testUpdatingCustomerBirthDate() {
        // given
        LocalDate newDate = LocalDate.of(1999, 6, 20);
        // when
        dao.updateCustomerBirthDate(customer.getLogin(), newDate);
        // then
        LocalDate updatedDate = dao.getCustomer(customer.getLogin()).getDateOfBirth();
        assertEquals(newDate, updatedDate);
    }
}