package controllers;

import daos.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;

public class AddingUserController {

    @FXML
    private Button confirmUser;

    @FXML
    private TextField name;

    @FXML
    private TextField lastname;

    @FXML
    private DatePicker birthDate;

    @FXML
    private ListView<?> userList;

    @FXML
    public void addUser(ActionEvent event) {
        String login = name.getText().substring(0, 3) + lastname.getText().substring(0, 3);
        Customer customer = new Customer(login ,name.getText(), lastname.getText(), birthDate.getValue());
        CustomerDao customerDao = new CustomerDao();
        customerDao.addCustomer(customer);
    }

}
