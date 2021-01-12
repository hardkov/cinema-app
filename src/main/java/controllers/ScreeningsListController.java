package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.*;

import javax.annotation.Resource;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class ScreeningsListController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();

    @FXML
    private ListView<Screening> screeningsList;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        loadData();
    }

    private void loadData(){
        list.removeAll(list);
        Movie movie1 = new Movie("Movie1", LocalDate.of(1999, 9, 12), MovieGenre.DRAMAT);
        Movie movie2 = new Movie("Movie2", LocalDate.of(1998, 9, 12), MovieGenre.KOMEDIA);
        MovieType movieType = MovieType.MOVIE_2D;
        Hall hall = new Hall(1, 20);
        int seatsLimit = 15;
        float basePrice = 15;

        Screening screening1 = new Screening(movie1, movieType, LocalTime.of(15, 20), hall, seatsLimit, basePrice);
        Screening screening2 = new Screening(movie2, movieType, LocalTime.of(17, 30), hall, seatsLimit, basePrice);
        screeningsList.getItems().addAll(screening1, screening2);
    }

    public void addScreening(ActionEvent event) {
        System.out.println("Add screening button pressed");
    }

    public void removeScreening(ActionEvent event) {
        System.out.println("Remove screening button pressed");
    }
}
