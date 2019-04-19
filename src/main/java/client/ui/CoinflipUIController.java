package client.ui;

import client.ui.UIController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class CoinflipUIController extends UIController {
    public Button back;
    public Button heads;
    public Button tails;


    public void handleButtonAction (ActionEvent event) throws IOException {
        //TODO: Server communication so player plays.
    }
    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back);
    }
}
