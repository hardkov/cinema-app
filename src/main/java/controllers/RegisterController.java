package controllers;

import daos.CustomerDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Customer;
import utils.Session;
import validators.CustomerValidators;
import validators.PasswordValidator;

import java.util.LinkedList;
import java.util.List;


public class RegisterController {
    Class cls = getClass();
    CustomerDao customerDao = new CustomerDao();

    @FXML
    public TextField login;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public PasswordField password;

    @FXML
    public DatePicker dateOfBirth;

    @FXML
    public TextField email;

    @FXML
    public Label actionInfo;

    public void register(ActionEvent event) {
        Customer customer = new Customer(login.getText(), name.getText(), surname.getText(),
                password.getText(), dateOfBirth.getValue(), email.getText());

        CustomerValidators customerValidators = new CustomerValidators();
        PasswordValidator passwordValidator = new PasswordValidator();
        List<String> feedback = new LinkedList<>();

        if(!customerValidators.isValid(customer, feedback) ||
                !passwordValidator.isValid(password.getText(), feedback)){
            actionInfo.setText("Invalid input");
            actionInfo.setTextFill(Color.RED);
        } else{
            customerDao.addCustomer(customer);
            Session.getSession().setCurrentUser(customer);
            Redirect.redirectTo(cls, event, "customerPanel.fxml");
        }
    }

    public void login(ActionEvent event) {
        Redirect.redirectTo(cls, event, "login.fxml");
    }
}
