package controllers;

import com.google.common.base.Predicates;
import comparators.GenreComparator;
import comparators.MovieCreatingDateComparator;
import comparators.TitleComparator;
import daos.MovieDao;
import daos.ScreeningDao;
import helpers.Redirect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import model.Movie;
import model.Screening;
import predicates.ScreeningPredicates;
import utils.Session;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomerScreeningsListController implements Initializable {
    private Class cls = getClass();
    private ScreeningDao screeningDao = new ScreeningDao();
    private MovieDao movieDao = new MovieDao();
    private FilteredList screeningFilteredList;
    private String noFilterTitleSelection = "All";

    @FXML
    public ListView<Movie> screeningsList;

    @FXML
    public ComboBox<String> title;

    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    public void home(ActionEvent event){
        Session.getSession().setCurrentlyViewedMovie(null);
        Redirect.redirectTo(cls, event, "customerPanel.fxml");
    }


    private void loadData(){
        List<Screening> allScreenings = screeningDao.getAllScreenings();
        List<String> allTitles = movieDao.getAllMovies().stream().map(m -> m.getTitle()).collect(Collectors.toList());
        allTitles.add(noFilterTitleSelection);

        ObservableList<Screening> screeningsObservableList = FXCollections.observableList(allScreenings);
        ObservableList<String> titlesObservableList = FXCollections.observableList(allTitles);

        Movie selectedMovie = Session.getSession().getCurrentlyViewedMovie();
        String selectedTitle;
        Predicate predicate;

        if(selectedMovie != null){
            selectedTitle = selectedMovie.getTitle();
            predicate = ScreeningPredicates.isScreeningMovieTitleEqual(selectedTitle);
        } else{
            selectedTitle = noFilterTitleSelection;
            predicate = ScreeningPredicates.alwaysTrue();
        }

        screeningFilteredList = screeningsObservableList.filtered(predicate);
        screeningsList.setItems(screeningFilteredList);

        title.setItems(titlesObservableList);
        title.getSelectionModel().select(selectedTitle);
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

    public void buyTicket(ActionEvent event) {
        System.out.println("Buy ticket button pressed");
    }
}
