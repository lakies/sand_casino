package Client.ui;

import client.ui.UIController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class VictoryController extends UIController {
    public Label coins;
    public Button button;
    //TODO: Label coins get victory amount from server;
    public void handleButtonAction (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", button);
    }

}
