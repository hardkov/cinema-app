package daos;

import model.Employee;
import model.Permission;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class EmployeeDaoTest {
    private EmployeeDao dao = new EmployeeDao();
    Employee employee = new Employee("gsalnin", "Grzegorz", "Janisz", Permission.WORKER);

    @Test
    public void testAddingAndGettingFromDataBase() {
        // given
        // when
        dao.removeEmployee(employee);
        dao.addEmployee(employee);
        Employee gotEmployee = dao.getEmployee(employee.getLogin());
        // then
        assertEquals(employee.getLogin(), gotEmployee.getLogin());
        assertEquals(employee.getName(), gotEmployee.getName());
        assertEquals(employee.getSurname(), gotEmployee.getSurname());
        assertEquals(employee.getPermissions(), gotEmployee.getPermissions());
        assertTrue(dao.doesEmployeeExist(employee.getLogin()));
    }

    @Test
    public void testAddingToDataBase() {
        // given
        Employee newEmployee = new Employee("newone", "Adrian", "Norka", Permission.WORKER);
        dao.removeEmployee(newEmployee);
        int prevSize = dao.getAllEmployees().size();
        // when
        dao.addEmployee(newEmployee);
        // then
        int expected = prevSize + 1;
        assertEquals(expected, dao.getAllEmployees().size());
    }

    @Test
    public void testRemovingFromDatabase() {
        // given
        Employee employeeToRemove = new Employee("newone2", "Adrian", "Norka", Permission.ADMIN);
        dao.addEmployee(employeeToRemove);
        // when
        dao.removeEmployee(employeeToRemove);
        // then
        assertFalse(dao.doesEmployeeExist(employeeToRemove.getLogin()));
    }

    @Test
    public void testUpdatingEmployeeSurname() {
        // given
        String newSurname = "Szymon";
        // when
        dao.updateEmployeeSurname(employee.getLogin(), newSurname);
        // then
        String updatedName = dao.getEmployee(employee.getLogin()).getName();
        assertEquals(newSurname, updatedName);
    }

    @Test
    public void testUpdatingEmployeePermissions() {
        // given
        Permission newPermissions = Permission.ADMIN;
        // when
        dao.updateEmployeePermissions(employee.getLogin(), newPermissions);
        // then
        Permission updatedPermissions = dao.getEmployee(employee.getLogin()).getPermissions();
        assertEquals(newPermissions, updatedPermissions);
    }
}