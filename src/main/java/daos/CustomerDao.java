package daos;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import helpers.DateConverter;
import model.Customer;
import model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static daos.DaoConstants.customerPath;
import static daos.DaoConstants.userPath;

public class CustomerDao {
    Firestore db;
    private final static String userField = "user";
    private final static String birthDateField = "dateOfBirth";
    private final static String emailField = "e-mail";

    public CustomerDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
    }

    public boolean addCustomer(Customer customer) {
        return addCustomer(customer, true);
    }

    private boolean addCustomer(Customer customer, boolean checkIfExists) {
        String login = customer.getLogin();
        if (checkIfExists && doesCustomerExist(login)) {
            return false;
        }
        // creating user
        UserDao userDao = new UserDao();
        User user = new User(customer.getLogin(), customer.getName(), customer.getSurname(), customer.getPassword(), customer.getSalt());
        // user with such login not exist or creating user failed
        if (!userDao.addUser(user)) {
            return false;
        }
        Map<String, Object> docData = new HashMap<>();
        DocumentReference userReference = db.collection(userPath).document(customer.getLogin());
        docData.put(userField, userReference);
        String dateString = new DateConverter().getDateString(customer.getDateOfBirth());
        docData.put(birthDateField, dateString);
        docData.put(emailField, customer.getEmail());
        ApiFuture<WriteResult> writeResult = db.collection(customerPath).document(login).set(docData);
        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return writeResult.isDone();
    }

    public Customer getCustomer(String login) {
        DocumentReference docRef = db.collection(customerPath).document(login);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        Customer customer = null;
        try {
            DocumentSnapshot document = future.get();
            if (document.exists()) {
                LocalDate customerBirthDate = new DateConverter().getLocalDateFromString(document.get(birthDateField, String.class));
                DocumentReference userReference = document.get(userField, DocumentReference.class);
                User user = new GenericDao<User>(User.class).getObject(userReference);
                String email = document.get(emailField, String.class);
                customer = new Customer(user.getLogin(), user.getName(), user.getSurname(), user.getPassword(), user.getSalt(),
                        customerBirthDate, email);
            }
            else {
                System.out.println(String.format("There is no document %s", docRef.getPath()));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public DocumentReference getCustomerReference(Customer customer) {
        return db.collection(customerPath).document(customer.getLogin());
    }

    public boolean removeALlCustomers(){
        boolean removeResult = true;
        ApiFuture<QuerySnapshot> future = db.collection(customerPath).get();
        List<QueryDocumentSnapshot> documents = null;
        try {
            documents = future.get().getDocuments();
            GenericDao<User> userGenericDao = new GenericDao<>(User.class);
            for (QueryDocumentSnapshot document : documents) {
                DocumentReference userReference = document.get(userField, DocumentReference.class);
                removeResult &= userGenericDao.removeObject(userReference);

                DocumentReference customerReference = document.getReference();

                ApiFuture<WriteResult> customerRemoveResult = customerReference.delete();;

                customerRemoveResult.get();

                removeResult |= customerRemoveResult.isDone();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return removeResult;
    }

    public List<Customer> getAllCustomers() {
        ApiFuture<QuerySnapshot> future = db.collection(customerPath).get();
        List<QueryDocumentSnapshot> documents = null;
        List<Customer> customers = new ArrayList<>();
        try {
            documents = future.get().getDocuments();
            GenericDao<User> userGenericDao = new GenericDao<>(User.class);
            for (QueryDocumentSnapshot document : documents) {
                LocalDate customerBirthDate = new DateConverter().getLocalDateFromString(document.get(birthDateField, String.class));
                DocumentReference userReference = document.get(userField, DocumentReference.class);
                User user = userGenericDao.getObject(userReference);
                String email = document.get(emailField, String.class);
                Customer customer = new Customer(user.getLogin(), user.getName(), user.getSurname(), user.getPassword(), user.getSalt(), customerBirthDate, email);
                customers.add(customer);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public boolean doesCustomerExist(String login) {
        return getCustomer(login) != null;
    }

    public boolean removeCustomer(Customer customer) {
        boolean removeResult = false;
        String login = customer.getLogin();
        DocumentReference customerRef = db.collection(customerPath).document(login);
        GenericDao<User> userGenericDao = new GenericDao<>(User.class);
        try {
            // remove related user
            DocumentReference userRef = customerRef.get().get().get(userField, DocumentReference.class);
            removeResult |= userGenericDao.removeObject(userRef);
            ApiFuture<WriteResult> customerRemoveResult = db.collection(customerPath).document(login).delete();
            customerRemoveResult.get();
            removeResult |= customerRemoveResult.isDone();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return removeResult;
    }

    public boolean updateCustomer(Customer customer) {
        return addCustomer(customer, false);
    }

    public boolean updateCustomerSurname(String login, String newSurname) {
        UserDao userDao = new UserDao();
        return userDao.updateUserName(login, newSurname);
    }

    public boolean updateCustomerName(String login, String newName) {
        UserDao userDao = new UserDao();
        return userDao.updateUserName(login, newName);
    }

    public boolean updateCustomerBirthDate(String login, LocalDate newDate) {
        GenericDao<Customer> customerGenericDao = new GenericDao<>(Customer.class);
        String newDateString = new DateConverter().getDateString(newDate);
        return customerGenericDao.updateField(customerPath, login, birthDateField, newDateString);
    }
}
