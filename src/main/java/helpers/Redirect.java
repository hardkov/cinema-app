package helpers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Redirect {
    public static void redirectTo(Class cls, ActionEvent event, String to){
        try{
            Parent pane = FXMLLoader.load(cls.getClassLoader().getResource(to));
            Scene scene = new Scene(pane);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}


