package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Customer;

import java.time.LocalDate;

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
    void addUser(ActionEvent event) {
        Customer customer = new Customer(name.getText(), lastname.getText(), birthDate.getValue());
    }

}
