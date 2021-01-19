package controllers;

import comparators.GenreComparator;
import comparators.MovieCreatingDateComparator;
import comparators.TitleComparator;
import daos.MovieDao;
import daos.TicketDao;
import helpers.Redirect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import model.Customer;
import model.Movie;
import model.Ticket;
import utils.Session;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CustomerTicketsListController implements Initializable {
    private Class cls = getClass();
    private TicketDao ticketDao = new TicketDao();

    @FXML
    public ListView<Ticket> ticketsList;

    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    public void home(ActionEvent event){
        Redirect.redirectTo(cls, event, "customerPanel.fxml");
    }

    private void loadData(){
        Customer currentCustomer = (Customer) Session.getSession().getCurrentUser();
        List<Ticket> allTickets = ticketDao.getAllTickets();
        List<Ticket> customerTickets = allTickets.stream().filter(t -> t.getCustomer().getLogin().equals(currentCustomer.getLogin())).collect(Collectors.toList());

        ObservableList<Ticket> observableList = FXCollections.observableList(customerTickets);
        ticketsList.setItems(observableList);
    }

    public void refund(ActionEvent event) {
        Ticket ticket = ticketsList.getSelectionModel().getSelectedItem();

        if(ticket != null){
            ticketDao.removeTicket(ticket);
        }

        loadData();
    }
}
