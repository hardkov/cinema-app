package controllers;

import daos.HallDao;
import helpers.Redirect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Hall;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HallsListController implements Initializable {
    private HallDao hallDao = new HallDao();

    @FXML
    public TextField seatsLimit;

    @FXML
    public TextField hallId;

    @FXML
    public ListView<Hall> hallsList;

    public void initialize(URL url, ResourceBundle rb){
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
        hallsList.getItems().addAll((hallDao.getAllHalls()));
    }

    public void addHall(ActionEvent event) {
        Hall hall = new Hall(Integer.parseInt(hallId.getText()), Integer.parseInt(seatsLimit.getText()));

        hallDao.addHall(hall);

        hallsList.getItems().add(hall);
    }

    public void removeHall(ActionEvent event) {
        Hall hall = hallsList.getSelectionModel().getSelectedItem();

        hallDao.removeHall(hall);

        hallsList.getItems().remove(hall);
    }
}
