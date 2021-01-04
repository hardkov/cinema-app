package controllers;

import daos.MovieDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Movie;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MoviesListController implements Initializable {
    private MovieDao movieDao = new MovieDao();

    @FXML
    public TextField title;

    @FXML
    public TextField genre;

    @FXML
    public DatePicker date;

    @FXML
    public ListView<Movie> moviesList;

    public void initialize(URL url, ResourceBundle rb) {
        loadData();
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
        moviesList.getItems().addAll(movieDao.getAllMovies());
    }

    public void addMovie(ActionEvent event) {
        Movie movie = new Movie(
                title.getText(),
                date.getValue(),
                genre.getText()
        );

        movieDao.addMovie(movie);

        moviesList.getItems().add(movie);
    }

    public void removeMovie(ActionEvent event) {
        Movie movie = moviesList.getSelectionModel().getSelectedItem();

        movieDao.removeMovie(movie);

        moviesList.getItems().remove(movie);
    }
}
