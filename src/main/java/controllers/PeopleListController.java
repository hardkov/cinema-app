package controllers;

import daos.CustomerDao;
import daos.UserDao;
import model.Customer;
import validators.UserValidators;
import comparators.LoginComparator;
import comparators.NameComparator;
import comparators.SurnameComparator;
import daos.EmployeeDao;
import helpers.Redirect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.Employee;
import model.Permission;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.User;

import java.net.URL;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class PeopleListController implements Initializable {
    private Class cls = getClass();
    private EmployeeDao employeeDao = new EmployeeDao();
    private UserDao userDao = new UserDao();
    private CustomerDao customerDao = new CustomerDao();
    private SortedList userSortedList;
    private ObservableList<Comparator<User>> userComparators;

    @FXML
    public Label errorInfo;

    @FXML
    public ComboBox<Permission> permissions;

    @FXML
    public ComboBox<Comparator<User>> userOrder;

    @FXML
    public TextField login;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public ListView<User> userList;

    public void initialize(URL url, ResourceBundle rb){
        loadData();
        permissions.getItems().setAll(Permission.values());
        userComparators = FXCollections.observableArrayList(
                new LoginComparator(),
                new SurnameComparator(),
                new NameComparator()
        );

        userOrder.setItems(userComparators);
        userOrder.getSelectionModel().selectFirst();
        permissions.getSelectionModel().selectFirst();
    }

    public void home(ActionEvent event){
        Redirect.redirectTo(cls, event, "adminPanel.fxml");
    }

    private void loadData(){
        List<User> list = new LinkedList<>();
        List<Customer> customers = customerDao.getAllCustomers();
        List<Employee> employees = employeeDao.getAllEmployees();

        if(customers != null) list.addAll(customerDao.getAllCustomers());
        if(employees != null) list.addAll(employeeDao.getAllEmployees());

        ObservableList<User> observableList = FXCollections.observableList(list);

        userSortedList = observableList.sorted();
        userList.setItems(userSortedList);
        errorInfo.setText("");
    }

    public void addPerson(ActionEvent event) {
        Employee employee = (new Employee(login.getText(), name.getText(), surname.getText(), null, null,
                permissions.getValue()));

        UserValidators userValidators = new UserValidators();
        if(userValidators.isValid(employee, null)){
            employeeDao.addEmployee(employee);
            loadData();
        } else{
            errorInfo.setText("Invalid user details");
            errorInfo.setTextFill(Color.RED);
        }
    }

    public void removePerson(ActionEvent event) {

        User user = userList.getSelectionModel().getSelectedItem();

        if(user instanceof Employee) employeeDao.removeEmployee((Employee) user);
        if(user instanceof Customer) customerDao.removeCustomer((Customer) user);

        loadData();
    }

    public void onSortByComparator(ActionEvent event) {
        userSortedList.setComparator(userOrder.getSelectionModel().getSelectedItem());
    }
}
