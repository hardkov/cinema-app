package controllers;

import daos.ScreeningDao;
import daos.TicketDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.*;
import statistics.PopularityMovieStatisticCreator;
import utils.Session;

import javax.mail.search.ReceivedDateTerm;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerPanelController implements Initializable {
    Class cls = getClass();

    @FXML
    public Label userWelcomeMessage;

    @FXML
    public Label recommendedMovieHeaderLabel;

    @FXML
    public Label recommendMovieLabel;

    private final static String headerForRecommendedMovie = "Recommended movie for you:";
    private final static String headerForLackOfRecommendedMovie = "Wow! You've seen all our movies! <3";

    public void initialize(URL url, ResourceBundle rb){
        User currentUser = Session.getSession().getCurrentUser();
        if(currentUser != null){
            this.userWelcomeMessage.setText("Welcome, " + currentUser.getLogin());
        }
        setRecommendedMovieForCustomer(currentUser);
    }

    private void setRecommendedMovieForCustomer(User user) {
        Customer customer = (Customer) user;
        PopularityMovieStatisticCreator creator = new PopularityMovieStatisticCreator();
        Movie recommendedMovie = creator.getMoviePropositionForCustomer(customer);
        if (recommendedMovie == null) {
            recommendMovieLabel.setText("");
            recommendedMovieHeaderLabel.setText(headerForLackOfRecommendedMovie);
        } else {
            recommendMovieLabel.setText(recommendedMovie.getTitle());
            recommendedMovieHeaderLabel.setText(headerForRecommendedMovie);
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
