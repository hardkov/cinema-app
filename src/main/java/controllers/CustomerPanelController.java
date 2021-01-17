package controllers;

import daos.ScreeningDao;
import daos.TicketDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Screening;
import model.Ticket;
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
//        TicketDao ticketDao = new TicketDao();
//        for(Ticket ticket : ticketDao.getAllTickets()){
//            System.out.println(ticket.getId());
//        }

        ScreeningDao screeningDao = new ScreeningDao();
        for(Screening screening : screeningDao.getAllScreenings()){
            System.out.println(screening.getId());
        }

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
        Redirect.redirectTo(cls, event, "customerTicketsList.fxml");
    }

    public void screenings(ActionEvent event) {
        Redirect.redirectTo(cls, event, "customerScreeningsList.fxml");
    }

    public void movies(ActionEvent event) {
        Redirect.redirectTo(cls, event, "customerMoviesList.fxml");
    }
}
