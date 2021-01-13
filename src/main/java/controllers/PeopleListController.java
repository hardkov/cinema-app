package controllers;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import model.Employee;
import model.Permission;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class PeopleListController implements Initializable {
    private EmployeeDao employeeDao = new EmployeeDao();
    private SortedList employeeSortedList;
    private ObservableList<Comparator<Employee>> employeeComparators;

    @FXML
    public ComboBox<Permission> permissions;

    @FXML
    public ComboBox<Comparator<Employee>> employeeOrder;

    @FXML
    public TextField login;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public ListView<Employee> employeeList;

    public void initialize(URL url, ResourceBundle rb){
        loadData();
        permissions.getItems().setAll(Permission.values());
        employeeComparators = FXCollections.observableArrayList(
                new LoginComparator(),
                new SurnameComparator(),
                new NameComparator()
        );

        employeeOrder.setItems(employeeComparators);
        employeeOrder.getSelectionModel().selectFirst();
    }

    public void home(ActionEvent event){
        try{
            Parent pane = FXMLLoader.load(getClass().getClassLoader().getResource("adminPanel.fxml"));
            Redirect.redirectTo(pane, event);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void loadData(){
        List<Employee> list = employeeDao.getAllEmployees();
        ObservableList<Employee> observableList = FXCollections.observableList(list);
        employeeSortedList = observableList.sorted();
        employeeList.setItems(employeeSortedList);
    }

    public void addPerson(ActionEvent event) {
        Employee employee = new Employee(login.getText(), name.getText(), surname.getText(), permissions.getValue());
        employeeDao.addEmployee(employee);
        loadData();
    }

    public void removePerson(ActionEvent event) {
        Employee employee = employeeList.getSelectionModel().getSelectedItem();
        employeeDao.removeEmployee(employee);
        loadData();
    }

    public void onSortByComparator(ActionEvent event) {
        employeeSortedList.setComparator(employeeOrder.getSelectionModel().getSelectedItem());
    }
}
