package controllers;

import com.google.common.base.Predicates;
import comparators.GenreComparator;
import comparators.MovieCreatingDateComparator;
import comparators.TitleComparator;
import daos.DiscountDao;
import daos.MovieDao;
import daos.ScreeningDao;
import daos.TicketDao;
import helpers.Redirect;
import helpers.TicketUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import model.*;
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
    private TicketDao ticketDao = new TicketDao();
    private DiscountDao discountDao = new DiscountDao();
    private FilteredList screeningFilteredList;

    private String noFilterTitleSelection = "All";
    private Discount noDiscountSelection = new Discount("None", 0);

    @FXML
    public ComboBox<Discount> discount;

    @FXML
    public ListView<Screening> screeningsList;

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
        List<Discount> allDiscounts = discountDao.getAllDiscounts();
        allDiscounts.add(noDiscountSelection);

        ObservableList<Screening> screeningsObservableList = FXCollections.observableList(allScreenings);
        ObservableList<String> titlesObservableList = FXCollections.observableList(allTitles);
        ObservableList<Discount> discountsObservableList = FXCollections.observableList(allDiscounts);

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

        discount.setItems(discountsObservableList);
        discount.getSelectionModel().select(noDiscountSelection);
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
        Screening screening = screeningsList.getSelectionModel().getSelectedItem();
        Discount selectedDiscount = discount.getSelectionModel().getSelectedItem();

        float price = screening.getBasePrice() * (1 - selectedDiscount.getValue());
        int seatId = TicketUtils.getFreeSeatId(screening);

        Ticket ticket = new Ticket(screening, (Customer) Session.getSession().getCurrentUser(), price, seatId);
        // validate ticket
        ticketDao.addTicket(ticket);

        Session.getSession().setCurrentlyViewedMovie(null);
        Redirect.redirectTo(cls, event, "customerPanel.fxml");
    }
}
