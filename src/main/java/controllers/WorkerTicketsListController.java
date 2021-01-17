package controllers;

import daos.TicketDao;
import helpers.Redirect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.Customer;
import model.Ticket;
import utils.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class WorkerTicketsListController implements Initializable {
    private Class cls = getClass();
    private TicketDao ticketDao = new TicketDao();

    @FXML
    public ListView<Ticket> ticketsList;

    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    public void home(ActionEvent event){
        Redirect.redirectTo(cls, event, "workerPanel.fxml");
    }

    private void loadData(){
        List<Ticket> allTickets = ticketDao.getAllTickets();

        ObservableList<Ticket> observableList = FXCollections.observableList(allTickets);
        ticketsList.setItems(observableList);
    }

    public void refund(ActionEvent event) {
        Ticket ticket = ticketsList.getSelectionModel().getSelectedItem();
        ticketDao.removeTicket(ticket);
        loadData();
    }
}
