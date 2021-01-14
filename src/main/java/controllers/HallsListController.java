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
        errorInfo.setText("");
    }

    public void home(ActionEvent event){
        Redirect.redirectTo(cls, event, "adminPanel.fxml");
    }

    private void loadData(){
        hallsList.getItems().setAll((hallDao.getAllHalls()));
    }

    public void addHall(ActionEvent event) {
        Integer hallIdInt, seatsLimitInt;

        try {
            hallIdInt = Integer.parseInt(hallId.getText());
            seatsLimitInt = Integer.parseInt(seatsLimit.getText());
        } catch (NumberFormatException e){
            errorInfo.setText("Invalid number format");
            errorInfo.setTextFill(Color.RED);
            return;
        }

        Hall hall = new Hall(hallIdInt, seatsLimitInt);

        HallValidators hallValidators = new HallValidators();
        if(hallValidators.isValid(hall, null)){
            System.out.println(hall);
            hallDao.addHall(hall);
            loadData();
        } else{
            errorInfo.setText("Invalid hall details");
            errorInfo.setTextFill(Color.RED);
        }
    }

    public void removeHall(ActionEvent event) {
        Hall hall = hallsList.getSelectionModel().getSelectedItem();

        hallDao.removeHall(hall);
        loadData();
    }
}
