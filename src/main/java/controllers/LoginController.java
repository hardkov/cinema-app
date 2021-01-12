package controllers;
import daos.UserDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import model.User;
import utils.Session;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    public void login(ActionEvent event) {
        String login = username.getText();
        String pass = password.getText();
        System.out.println(login);
        System.out.println(password.getText());

        UserDao userDao = new UserDao();
        User user = userDao.getUser(login);
        if (user != null && user.verifyPassword(pass)) {
            try{
                Parent pane = FXMLLoader.load(getClass().getClassLoader().getResource("adminPanel.fxml"));
                Redirect.redirectTo(pane, event);
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}
