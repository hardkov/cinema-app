package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import helpers.DateConverter;
import model.Customer;
import model.Employee;
import model.Permission;
import model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static daos.DaoConstants.*;

public class EmployeeDao {

    Firestore db;
    private final static String userField = "user";
    private final static String permissionsField = "permissions";

    public EmployeeDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
    }

    public boolean addEmployee(Employee employee) {
        String login = employee.getLogin();
        if (doesEmployeeExist(login)) {
            return false;
        }
        // creating user
        UserDao userDao = new UserDao();
        User user = new User(employee.getLogin(), employee.getName(), employee.getSurname());
        // user with such login not exist or creating user failed
        if (!userDao.addUser(user)) {
            return false;
        }
        Map<String, Object> docData = new HashMap<>();
        DocumentReference userReference = db.collection(userPath).document(employee.getLogin());
        docData.put(userField, userReference);
        docData.put(permissionsField, employee.getPermissions());
        ApiFuture<WriteResult> writeResult = db.collection(employeePath).document(login).set(docData);
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }

    public Employee getEmployee(String login) {
        DocumentReference docRef = db.collection(employeePath).document(login);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        Employee employee = null;
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                Permission permission = document.get(permissionsField, Permission.class);
                DocumentReference userReference = document.get(userField, DocumentReference.class);
                GenericDao<User> genericDao = new GenericDao<>(User.class);
                User user = genericDao.getObject(userReference);
                return new Employee(user.getLogin(), user.getName(), user.getSurname(), permission);
            }
            else {
                System.out.println(String.format("There is no document %s", docRef.getPath()));
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        ApiFuture<QuerySnapshot> future = db.collection(employeePath).get();
        List<QueryDocumentSnapshot> documents = null;
        List<Employee> employees = new ArrayList<>();
        try {
            documents = future.get().getDocuments();
            GenericDao<User> userGenericDao = new GenericDao<>(User.class);
            for (QueryDocumentSnapshot document : documents) {
                Permission permission = document.get(permissionsField, Permission.class);
                DocumentReference userReference = document.get(userField, DocumentReference.class);
                User user = userGenericDao.getObject(userReference);
                Employee employee = new Employee(user.getLogin(), user.getName(), user.getSurname(), permission);
                employees.add(employee);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public boolean doesEmployeeExist(String login) {
        return getEmployee(login) != null;
    }

    public boolean removeEmployee(Employee employee) {
        String login = employee.getLogin();
        boolean removeResult = false;
        DocumentReference employeeRef = db.collection(employeePath).document(login);
        GenericDao<User> userGenericDao = new GenericDao<>(User.class);
        try {
            // remove related user
            DocumentReference userRef = employeeRef.get().get().get(userField, DocumentReference.class);
            removeResult |= userGenericDao.removeObject(userRef);
            ApiFuture<WriteResult> employeeRemoveResult = employeeRef.delete();
            employeeRemoveResult.get();
            removeResult |= employeeRemoveResult.isDone();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return removeResult;
    }

    public boolean updateEmployee(Employee employee) {
        return addEmployee(employee);
    }

    public boolean updateEmployeeSurname(String login, String newSurname) {
        UserDao userDao = new UserDao();
        return userDao.updateUserName(login, newSurname);
    }

    public boolean updateEmployeeName(String login, String newName) {
        UserDao userDao = new UserDao();
        return userDao.updateUserName(login, newName);
    }

    public boolean updateEmployeePermissions(String login, Permission permission) {
        GenericDao<Employee> employeeGenericDao = new GenericDao<>(Employee.class);
        return employeeGenericDao.updateField(employeePath, login, permissionsField, permission);
    }
}
