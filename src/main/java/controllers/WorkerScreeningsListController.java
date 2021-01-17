package controllers;

import daos.*;
import helpers.Redirect;
import helpers.TicketUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;
import predicates.ScreeningPredicates;
import utils.Session;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WorkerScreeningsListController implements Initializable {
    private Class cls = getClass();
    private ScreeningDao screeningDao = new ScreeningDao();
    private MovieDao movieDao = new MovieDao();
    private HallDao hallDao = new HallDao();
    private FilteredList screeningFilteredList;

    private String noFilterTitleSelection = "All";

    @FXML
    public TextField seatsLimit;

    @FXML
    public TextField basePrice;

    @FXML
    public ComboBox<Hall> hall;

    @FXML
    public DatePicker date;

    @FXML
    public TextField time;

    @FXML
    public ComboBox<MovieType> movieType;

    @FXML
    public ComboBox<String> movie;

    @FXML
    public ListView<Screening> screeningsList;

    @FXML
    public ComboBox<String> title;

    public void initialize(URL url, ResourceBundle rb) {
        loadData();

        List<String> allTitles = movieDao.getAllMovies().stream().map(m -> m.getTitle()).collect(Collectors.toList());
        allTitles.add(noFilterTitleSelection);
        List<MovieType> allMovieTypes = Arrays.asList(MovieType.values().clone());
        List<Hall> allHalls = hallDao.getAllHalls();

        title.getItems().setAll(allTitles);
        title.getSelectionModel().select(noFilterTitleSelection);

        movie.getItems().setAll(allTitles);
        movie.getSelectionModel().selectFirst();

        movieType.getItems().setAll(allMovieTypes);
        movieType.getSelectionModel().selectFirst();

        hall.getItems().setAll(allHalls);
        hall.getSelectionModel().selectFirst();
    }

    public void home(ActionEvent event){
        Session.getSession().setCurrentlyViewedMovie(null);
        Redirect.redirectTo(cls, event, "workerPanel.fxml");
    }

    private void loadData(){
        List<Screening> allScreenings = screeningDao.getAllScreenings();

        ObservableList<Screening> screeningsObservableList = FXCollections.observableList(allScreenings);

        screeningFilteredList = screeningsObservableList.filtered(ScreeningPredicates.alwaysTrue());
        screeningsList.setItems(screeningFilteredList);
        screeningsList.getSelectionModel().selectFirst();
    }

    public void onFilterByTitle(ActionEvent event) {
        String selectedTitle = title.getSelectionModel().getSelectedItem();
        Predicate predicate;

        if(selectedTitle != noFilterTitleSelection){
            predicate = ScreeningPredicates.isScreeningMovieTitleEqual(selectedTitle);
        } else{
            predicate = ScreeningPredicates.alwaysTrue();
        }

        screeningFilteredList.setPredicate(predicate);
    }

    public void add(ActionEvent event) {
        // validate input from user

        Movie movieObj = movieDao.getMovie(movie.getSelectionModel().getSelectedItem());
        MovieType movieTypeObj = movieType.getSelectionModel().getSelectedItem();
        LocalDate dateObj = date.getValue();
        LocalTime timeObj = LocalTime.parse(time.getText());
        Hall hallObj = hall.getSelectionModel().getSelectedItem();
        int seatsLimitValue = Integer.valueOf(seatsLimit.getText());
        float basePriceValue = Float.valueOf(basePrice.getText());

        Screening screening = new Screening(movieObj, movieTypeObj, timeObj, dateObj, hallObj,
                seatsLimitValue, basePriceValue);
        // validate screening
        screeningDao.addScreening(screening);
        loadData();
    }

    public void remove(ActionEvent event) {
        Screening screening = screeningsList.getSelectionModel().getSelectedItem();
        screeningDao.removeScreening(screening);
        loadData();
    }
}
