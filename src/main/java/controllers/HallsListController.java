package controllers;

import daos.HallDao;
import helpers.Redirect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Hall;
import validators.HallValidators;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HallsListController implements Initializable {
    private Class cls = getClass();
    private HallDao hallDao = new HallDao();

    @FXML
    public Label errorInfo;

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
        Redirect.redirectTo(cls, event, "adminPanel.fxml");
    }

    private void loadData(){
        hallsList.getItems().setAll((hallDao.getAllHalls()));
        errorInfo.setText("");
    }

    public void addHall(ActionEvent event) {
        int hallIdInt = -1;
        int seatsLimitInt = -1;

        try {
            seatsLimitInt = Integer.parseInt(seatsLimit.getText());
        } catch (NumberFormatException e){
        }

        try {
            hallIdInt = Integer.parseInt(hallId.getText());
        } catch (NumberFormatException e){
        }

        Hall hall = new Hall(hallIdInt, seatsLimitInt);

        HallValidators hallValidators = new HallValidators();
        LinkedList<String> feedback = new LinkedList<>();
        if(hallValidators.isValid(hall, feedback)){
            hallDao.addHall(hall);
            loadData();
        } else{
            errorInfo.setText(feedback.getFirst());
            errorInfo.setTextFill(Color.RED);
        }
    }

    public void removeHall(ActionEvent event) {
        Hall hall = hallsList.getSelectionModel().getSelectedItem();

        if(hall != null){
            hallDao.removeHall(hall);
        }

        loadData();
    }
}
