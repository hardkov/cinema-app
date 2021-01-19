package controllers;

import daos.*;
import helpers.Redirect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.*;
import predicates.ScreeningPredicates;
import utils.Session;
import validators.ScreeningValidators;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.LinkedList;
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
    public Label errorInfo;

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

        errorInfo.setText("");
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
        int seatsLimitValue = -1;
        float basePriceValue = -1;
        LocalTime timeObj = null;

        try{
            seatsLimitValue = Integer.valueOf(seatsLimit.getText());
        } catch (NumberFormatException e){
        }

        try{
            basePriceValue = Float.valueOf(basePrice.getText());
        } catch (NumberFormatException e){
        }

        try{
            timeObj = LocalTime.parse(time.getText());
        } catch (DateTimeParseException e){
        }

        Movie movieObj = movieDao.getMovie(movie.getSelectionModel().getSelectedItem());
        MovieType movieTypeObj = movieType.getSelectionModel().getSelectedItem();
        LocalDate dateObj = date.getValue();
        Hall hallObj = hall.getSelectionModel().getSelectedItem();


        Screening screening = new Screening(movieObj, movieTypeObj, timeObj, dateObj, hallObj,
                seatsLimitValue, basePriceValue);

        ScreeningValidators screeningValidators = new ScreeningValidators();
        LinkedList<String> feedback = new LinkedList<>();
        if(screeningValidators.isValid(screening, feedback)){
            screeningDao.addScreening(screening);
            loadData();
        } else{
            errorInfo.setText(feedback.getFirst());
            errorInfo.setTextFill(Color.RED);
        }
    }

    public void remove(ActionEvent event) {
        Screening screening = screeningsList.getSelectionModel().getSelectedItem();
        screeningDao.removeScreening(screening);
        loadData();
    }
}
