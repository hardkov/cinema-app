package controllers;

import daos.UserDao;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import model.User;
import utils.PasswordUtils;
import utils.Session;

public class SetEmployeePasswordController {
    private Class cls = getClass();
    private UserDao userDao = new UserDao();

    @FXML
    public PasswordField password;

    public void setPassword(ActionEvent event) {
        User currentUser = Session.getSession().getCurrentUser();
        String salt = PasswordUtils.getSalt();
        String passwordEncrypted = PasswordUtils.generateSecurePassword(password.getText(), salt);

        currentUser.setSalt(salt);
        currentUser.setPassword(passwordEncrypted);

        userDao.updateUser(currentUser);

        Redirect.redirectTo(cls, event, "employeePanel.fxml");
    }
}
