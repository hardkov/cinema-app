package controllers;

import daos.EmployeeDao;
import daos.UserDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import model.Employee;
import model.Permission;
import model.User;
import utils.PasswordUtils;
import utils.Session;
import validators.PasswordValidator;
import validators.UserValidators;

import java.util.LinkedList;

public class SetEmployeePasswordController {
    private Class cls = getClass();
    private UserDao userDao = new UserDao();


    @FXML
    public Label errorInfo;

    @FXML
    public PasswordField password;

    public void setPassword(ActionEvent event) {
        PasswordValidator passwordValidator = new PasswordValidator();
        LinkedList<String> feedback = new LinkedList<>();
        if(!passwordValidator.isValid(password.getText(), feedback)){
            errorInfo.setText(feedback.getFirst());
            errorInfo.setTextFill(Color.RED);
            return;
        }

        User currentUser = Session.getSession().getCurrentUser();
        String salt = PasswordUtils.getSalt();
        String passwordEncrypted = PasswordUtils.generateSecurePassword(password.getText(), salt);

        currentUser.setSalt(salt);
        currentUser.setPassword(passwordEncrypted);

        userDao.updateUser(currentUser);

        Permission permission = PasswordUtils.authorize(currentUser);
        if(permission == Permission.ADMIN){
            Redirect.redirectTo(cls, event, "adminPanel.fxml");
        }else  if(permission == Permission.WORKER){
            Redirect.redirectTo(cls, event, "workerPanel.fxml");
        }else{
            Redirect.redirectTo(cls, event, "login.fxml");
        }
    }
}
