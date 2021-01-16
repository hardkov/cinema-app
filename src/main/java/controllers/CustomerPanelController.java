package controllers;

import helpers.Redirect;
import javafx.event.ActionEvent;
import utils.Session;

import javax.mail.search.ReceivedDateTerm;

public class CustomerPanelController {
    Class cls = getClass();

    public void logout(ActionEvent event) {
        Session.getSession().setCurrentUser(null);
        Redirect.redirectTo(cls, event, "login.fxml");
    }

    public void tickets(ActionEvent event) {
        System.out.println("Tickets button pressed");
    }

    public void screenings(ActionEvent event) {
        System.out.println("Screenings button pressed");
    }

    public void movies(ActionEvent event) {
        Redirect.redirectTo(cls, event, "customerMoviesList.fxml");
    }
}
