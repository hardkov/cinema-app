package controllers;
import Validators.PasswordValidator;
import Validators.UserValidators;
import daos.UserDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.User;
import utils.PasswordUtils;
import utils.Session;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class LoginController {

    @FXML
    public Label actionInfo;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    public void login(ActionEvent event) {
        String login = username.getText();
        String pass = password.getText();

        UserDao userDao = new UserDao();
        User user = userDao.getUser(login);

        if (user != null && user.verifyPassword(pass)) {

            Session.getSession().setCurrentUser(user);
            try{
                Parent pane = FXMLLoader.load(getClass().getClassLoader().getResource("adminPanel.fxml"));
                Redirect.redirectTo(pane, event);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        } else{
            actionInfo.setText("Wrong credentials! Please try again");
            actionInfo.setTextFill(Color.RED);
        }
    }
}
