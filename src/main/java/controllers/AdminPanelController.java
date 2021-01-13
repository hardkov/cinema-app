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
        try{
            Parent pane = FXMLLoader.load(getClass().getClassLoader().getResource("peopleList.fxml"));
            Redirect.redirectTo(pane, event);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void halls(ActionEvent event) {
        try{
            Parent pane = FXMLLoader.load(getClass().getClassLoader().getResource("hallsList.fxml"));
            Redirect.redirectTo(pane, event);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void movies(ActionEvent event) {
        try{
            Parent pane = FXMLLoader.load(getClass().getClassLoader().getResource("moviesList.fxml"));
            Redirect.redirectTo(pane, event);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void discounts(ActionEvent event) {
        System.out.println("Discounts button pressed");
    }

    public void logout(ActionEvent event) {
        try{
            Session.getSession().setCurrentUser(null);
            Parent pane = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Redirect.redirectTo(pane, event);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
