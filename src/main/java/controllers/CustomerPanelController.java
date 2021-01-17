package controllers;

import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.User;
import utils.Session;

import javax.mail.search.ReceivedDateTerm;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPanelController implements Initializable {
    Class cls = getClass();

    @FXML
    public Label userWelcomeMessage;

    public void initialize(URL url, ResourceBundle rb){
        User currentUser = Session.getSession().getCurrentUser();
        if(currentUser != null){
            this.userWelcomeMessage.setText("Welcome, " + currentUser.getLogin());
        }

    }

    public void logout(ActionEvent event) {
        Session.getSession().setCurrentUser(null);
        Redirect.redirectTo(cls, event, "login.fxml");
    }

    public void tickets(ActionEvent event) {
        System.out.println("Tickets button pressed");
    }

    public void screenings(ActionEvent event) {
        Redirect.redirectTo(cls, event, "customerScreeningsList.fxml");
    }

    public void movies(ActionEvent event) {
        Redirect.redirectTo(cls, event, "customerMoviesList.fxml");
    }
}
