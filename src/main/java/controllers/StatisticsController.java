package controllers;

import helpers.Redirect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.MovieGenre;
import statistics.*;

import java.net.URL;
import java.util.*;

public class StatisticsController implements Initializable {
    private Class cls = getClass();
    private SortedList<MovieStatistic> moviesSortedList;
    private FilteredList movieFilteredList;
    private ObservableList<MovieStatisticCreator> movieStatisticCreators;

    @FXML
    public TextField title;

    @FXML
    public ComboBox<MovieGenre> genreComboBox;

    @FXML
    public DatePicker date;

    @FXML
    public ListView<MovieStatistic> moviesList;

    @FXML
    public ComboBox<MovieStatisticCreator> moviesSortOrder;

    @FXML
    public ComboBox<SortOrder> moviesSortDirection;

    @FXML
    public CheckBox filterCheckBox;

    public void initialize(URL url, ResourceBundle rb) {
        genreComboBox.getItems().setAll(MovieGenre.values());

        movieStatisticCreators = FXCollections.observableArrayList(
                new PopularityMovieStatisticCreator(),
                new EarningsMovieStatisticCreator()
        );
        moviesSortOrder.getItems().setAll(movieStatisticCreators);
        moviesSortOrder.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            onChangeMovieStatisticCreator();
        });

        moviesSortDirection.getItems().setAll(SortOrder.values());
        moviesSortDirection.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            onChangeSortDirection();
        });

        filterCheckBox.selectedProperty().setValue(false);
        filterCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            onChangeFilterGenre();
        });
        genreComboBox.disableProperty().bind(filterCheckBox.selectedProperty().not());
        moviesList.setCellFactory(param -> new CellFactory());

        moviesSortOrder.getSelectionModel().selectFirst();
        moviesSortDirection.getSelectionModel().selectFirst();
        genreComboBox.getSelectionModel().selectFirst();
        createSortedMovieList();
    }

    public void home(ActionEvent event){
        Redirect.redirectTo(cls, event, "adminPanel.fxml");
    }

    @FXML
    public void onChangeMovieStatisticCreator() {
        createSortedMovieList();
    }

    @FXML
    public void onChangeFilterGenre() {
        if (!filterCheckBox.selectedProperty().getValue()) {
            moviesList.setItems((moviesSortedList));
            return;
        }
        MovieGenre selectedGenre = genreComboBox.selectionModelProperty().get().getSelectedItem();
        movieFilteredList = moviesSortedList.filtered((MovieStatistic statistic) ->
                statistic.getMovie().getGenre().equals(selectedGenre));
        moviesList.setItems(movieFilteredList);
    }

    @FXML
    public void onChangeSortDirection() {
        MovieStatisticCreator creator = moviesSortOrder.selectionModelProperty().get().getSelectedItem();
        SortOrder sortOrder = moviesSortDirection.getValue();
        moviesSortedList.setComparator(creator.getComparator(sortOrder));
    }

    private void createSortedMovieList() {
        MovieStatisticCreator creator = moviesSortOrder.selectionModelProperty().get().getSelectedItem();
        System.out.println(creator);
        ObservableList<MovieStatistic> observableList = FXCollections.observableList(creator.getMovieStatistics(null));
        moviesSortedList = observableList.sorted();
        SortOrder sortOrder = moviesSortDirection.getValue();
        moviesSortedList.setComparator(creator.getComparator(sortOrder));
        moviesList.setItems(moviesSortedList);
    }
}
