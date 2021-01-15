package controllers;

import helpers.Redirect;
import javafx.event.ActionEvent;
import utils.Session;

public class WorkerPanelController {
    Class cls = getClass();
    public void screenings(ActionEvent event) {
        System.out.println("Screenings button pressed");
    }

    public void tickets(ActionEvent event) {
        System.out.println("Tickets button pressed");
    }

    public void logout(ActionEvent event) {
        Session.getSession().setCurrentUser(null);
        Redirect.redirectTo(cls, event, "login.fxml");
    }
}
