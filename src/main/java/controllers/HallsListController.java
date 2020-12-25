package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class HallsListController {
    @FXML
    public TextField seatsLimit;

    @FXML
    public TextField hallId;

    @FXML
    public ListView hallsList;

    public void addHall(ActionEvent event) {
        System.out.println("Add hall button pressed");
        System.out.println(hallId.getText());
        System.out.println(seatsLimit.getText());
    }

    public void removeHall(ActionEvent event) {
        System.out.println("Remove hall button pressed");
    }
}
