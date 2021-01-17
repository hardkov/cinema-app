package controllers;

import comparators.GenreComparator;
import comparators.MovieCreatingDateComparator;
import comparators.TitleComparator;
import daos.MovieDao;
import helpers.Redirect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.Movie;
import model.MovieGenre;
import utils.Session;
import validators.MovieValidators;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerMoviesListController implements Initializable {
    private Class cls = getClass();
    private MovieDao movieDao = new MovieDao();
    private SortedList moviesSortedList;
    private ObservableList<Comparator<Movie>> moviesComparators;

    @FXML
    public ListView<Movie> moviesList;

    @FXML
    public ComboBox<Comparator<Movie>> moviesOrder;

    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        moviesComparators = FXCollections.observableArrayList(
                new TitleComparator(),
                new MovieCreatingDateComparator(),
                new GenreComparator()
        );
        moviesOrder.setItems(moviesComparators);
        moviesOrder.getSelectionModel().selectFirst();
    }

    public void home(ActionEvent event){
        Redirect.redirectTo(cls, event, "customerPanel.fxml");
    }


    private void loadData(){
        List<Movie> list = movieDao.getAllMovies();
        ObservableList<Movie> observableList = FXCollections.observableList(list);
        moviesSortedList = observableList.sorted();
        moviesList.setItems(moviesSortedList);
    }

    @FXML
    public void onSortByComparator() {
        moviesSortedList.setComparator(moviesOrder.getSelectionModel().getSelectedItem());
    }

    public void screenings(ActionEvent event) {
        Movie selectedMovie = moviesList.getSelectionModel().getSelectedItem();
        Session.getSession().setCurrentlyViewedMovie(selectedMovie);
        Redirect.redirectTo(cls, event, "customerScreeningsList.fxml");
    }
}
