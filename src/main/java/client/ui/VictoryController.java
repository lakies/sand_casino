package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class VictoryController {
    public Label coins;
    public Button button;
    //TODO: Label coins get victory amount from server;
    public void handleButtonAction (ActionEvent event) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gameChoiceScreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

}
