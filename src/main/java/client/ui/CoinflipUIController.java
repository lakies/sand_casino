package Client.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class CoinflipUIController  {
    public Button back;
    public Button heads;
    public Button tails;


    public void handleButtonAction (ActionEvent event) throws IOException {
        //TODO: Server communication so player plays.
    }
    public void goBack (ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gameChoiceScreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }
}
