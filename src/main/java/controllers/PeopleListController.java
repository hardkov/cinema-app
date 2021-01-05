package controllers;

import daos.UserDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import model.User;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PeopleListController implements Initializable {
    private UserDao userDao = new UserDao();

    @FXML
    public TextField login;

    @FXML
    public TextField name;

    @FXML
    public TextField surname;

    @FXML
    public ListView<User> peopleList;

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
        peopleList.getItems().addAll(userDao.getAllUsers());
    }

    public void addPerson(ActionEvent event) {
        User person = new User(login.getText(), name.getText(), surname.getText());

        userDao.addUser(person);

        peopleList.getItems().add(person);
    }

    public void removePerson(ActionEvent event) {
        User person = peopleList.getSelectionModel().getSelectedItem();

        userDao.removeUser(person);

        peopleList.getItems().remove(person);
    }
}
