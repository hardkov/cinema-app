package controllers;

import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class AdminPanelController {
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
            Parent pane = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
            Redirect.redirectTo(pane, event);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
