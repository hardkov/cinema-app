package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    public void login(ActionEvent event) {
        System.out.println(username.getText());
        System.out.println(password.getText());
    }
}
