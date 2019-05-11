package client.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class ConnectionLostUIController extends UIController {
    public Button logScreen;
    public void logScreen (ActionEvent event) throws IOException {
        sceneTransition("/logInScreen.fxml", logScreen);
    }


}
