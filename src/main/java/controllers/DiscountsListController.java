package controllers;

import daos.DiscountDao;
import daos.HallDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Discount;
import model.Hall;
import validators.DiscountValidators;
import validators.HallValidators;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class DiscountsListController implements Initializable {
    private Class cls = getClass();
    private DiscountDao discountDao = new DiscountDao();

    @FXML
    public Label errorInfo;

    @FXML
    public TextField name;

    @FXML
    public TextField value;

    @FXML
    public ListView<Discount> discountsList;

    public void initialize(URL url, ResourceBundle rb){
        loadData();
        errorInfo.setText("");
    }

    public void home(ActionEvent event){
        Redirect.redirectTo(cls, event, "adminPanel.fxml");
    }

    private void loadData(){
        discountsList.getItems().setAll(discountDao.getAllDiscounts());
        discountsList.getSelectionModel().selectFirst();
    }

    public void addDiscount(ActionEvent event) {
        Float valueObj;

        try {
            valueObj = Float.parseFloat(value.getText());
        } catch (NumberFormatException e){
            errorInfo.setText("Invalid number format");
            errorInfo.setTextFill(Color.RED);
            return;
        }

        Discount discount = new Discount(name.getText(), valueObj);

        DiscountValidators discountValidators = new DiscountValidators();
        LinkedList<String> feedback = new LinkedList<>();
        if(discountValidators.isValid(discount, feedback)){
            discountDao.addDiscount(discount);
            loadData();
        } else{
            errorInfo.setText("Invalid discount data");
            errorInfo.setTextFill(Color.RED);
        }
    }

    public void removeHall(ActionEvent event) {
        Discount discount = discountsList.getSelectionModel().getSelectedItem();
        discountDao.removeDiscount(discount);
        loadData();
    }

}
