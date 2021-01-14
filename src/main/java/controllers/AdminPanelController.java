package controllers;

import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import model.User;
import utils.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {
    private Class cls = getClass();

    @FXML
    public Label userWelcomeMessage;

    public void initialize(URL url, ResourceBundle rb){
        User currentUser = Session.getSession().getCurrentUser();
        if(currentUser != null){
            this.userWelcomeMessage.setText("Welcome, " + currentUser.getLogin());
        }

    }

    public void statistics(ActionEvent event) {
        System.out.println("Statistics button pressed");
    }

    public void people(ActionEvent event) {
        Redirect.redirectTo(cls, event, "peopleList.fxml");
    }

    public void halls(ActionEvent event) {
        Redirect.redirectTo(cls, event, "hallsList.fxml");
    }

    public void movies(ActionEvent event) {
        Redirect.redirectTo(cls, event, "moviesList.fxml");
    }

    public void discounts(ActionEvent event) {
        System.out.println("Discounts button pressed");
    }

    public void logout(ActionEvent event) {
        Redirect.redirectTo(cls, event, "login.fxml");
    }
}
