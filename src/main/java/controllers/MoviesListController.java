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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Movie;
import model.MovieGenre;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class MoviesListController implements Initializable {
    private MovieDao movieDao = new MovieDao();
    private SortedList moviesSortedList;
    private ObservableList<Comparator<Movie>> moviesComparators;

    @FXML
    public TextField title;

    @FXML
    public ComboBox<MovieGenre> genre;

    @FXML
    public DatePicker date;

    @FXML
    public ListView<Movie> moviesList;

    @FXML
    public ComboBox<Comparator<Movie>> moviesOrder;

    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        genre.getItems().setAll(MovieGenre.values());
        moviesComparators = FXCollections.observableArrayList(
                new TitleComparator(),
                new MovieCreatingDateComparator(),
                new GenreComparator()
        );
        moviesOrder.setItems(moviesComparators);
        moviesOrder.getSelectionModel().selectFirst();
    }

    public void home(ActionEvent event){
        try{
            Parent pane = FXMLLoader.load(getClass().getClassLoader().getResource("adminPanel.fxml"));
            Redirect.redirectTo(pane, event);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private void loadData(){
        List<Movie> list = movieDao.getAllMovies();
        ObservableList<Movie> observableList = FXCollections.observableList(list);
        moviesSortedList = observableList.sorted();
        moviesList.setItems(moviesSortedList);
    }

    public void addMovie(ActionEvent event) {
        Movie movie = new Movie(
                title.getText(),
                date.getValue(),
                genre.getValue()
        );

        movieDao.addMovie(movie);
        loadData();
    }

    public void removeMovie(ActionEvent event) {
        Movie movie = moviesList.getSelectionModel().getSelectedItem();
        movieDao.removeMovie(movie);
        loadData();
    }

    @FXML
    public void onSortByComparator() {
        moviesSortedList.setComparator(moviesOrder.getSelectionModel().getSelectedItem());
    }
}
