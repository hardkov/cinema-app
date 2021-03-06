package controllers;
import daos.*;
import helpers.EmailService;
import helpers.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.*;
import statistics.PopularityMovieStatisticCreator;
import utils.PasswordUtils;
import utils.Session;

import java.io.IOException;
import java.security.Permissions;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class LoginController {
    private Class cls = getClass();
    EmployeeDao employeeDao = new EmployeeDao();
    CustomerDao customerDao = new CustomerDao();

    private final static String contentForRecommendedMovie = "Recommended movie for you: ";
    private final static String subjectForRecommendedMovie = "Recommended movie";

    @FXML
    public Label actionInfo;

    @FXML
    private TextField username;

    @FXML
    private TextField password;


    private boolean handleUserAuth(User user, String pass, ActionEvent event){
        if(user.getPassword() == null){
            Session.getSession().setCurrentUser(user);
            Redirect.redirectTo(cls, event, "setEmployeePassword.fxml");
            return true;
        } else if(user.verifyPassword(pass)){
            Session.getSession().setCurrentUser(user);
            Permission permission = PasswordUtils.authorize(user);

            if(permission == Permission.ADMIN){
                Redirect.redirectTo(cls, event, "adminPanel.fxml");
            } else if(permission == Permission.WORKER) {
                Redirect.redirectTo(cls, event, "workerPanel.fxml");
            } else{
                EmailService emailService = new EmailService();
                PopularityMovieStatisticCreator creator = new PopularityMovieStatisticCreator();

                String email = ((Customer) user).getEmail();
                Movie recommendedMovie = creator.getMoviePropositionForCustomer((Customer) user);

                if(recommendedMovie != null){
                    emailService.sendMail(email, subjectForRecommendedMovie,
                            contentForRecommendedMovie + recommendedMovie.getTitle());
                }

                Redirect.redirectTo(cls, event, "customerPanel.fxml");
            }

            return true;
        }

        return false;
    }

    @FXML
    public void login(ActionEvent event) {
        String login = username.getText();
        String pass = password.getText();
        Employee employee = null;
        Customer customer = null;

        try {
            employee = employeeDao.getEmployee(login);
        } catch (Exception e){
        }

        try {
            customer = customerDao.getCustomer(login);
        } catch (Exception e){
        }

        User user = employee != null ? employee : customer;

        if (user == null || !handleUserAuth(user, pass, event)) {
            actionInfo.setText("Wrong credentials! Please try again");
            actionInfo.setTextFill(Color.RED);
        }
    }

    @FXML
    public void onEnter(ActionEvent ae){
        login(ae);
    }

    public void register(ActionEvent event) {
        Redirect.redirectTo(cls, event, "register.fxml");
    }
}
