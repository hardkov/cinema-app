package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MoviesListController {
    @FXML
    public TextField title;

    @FXML
    public TextField genre;

    @FXML
    public DatePicker date;

    @FXML
    public ListView MoviesList;

    public void addMovie(ActionEvent event) {
        System.out.println("Add movie button pressed");
    }

    public void removeMovie(ActionEvent event) {
        System.out.println("Remove movie button pressed");
    }
}
