package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import protocol.MessageType;
import protocol.Response;
import protocol.requests.UserDataRequest;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LotteryUIController extends UIController implements Initializable {
    public Button submit;
    public Label coins;
    public Button back;
    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #4eb5f1;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #4eb5f1; -fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submit.setStyle(IDLE_BUTTON_STYLE);
        submit.setOnMouseEntered(e -> submit.setStyle(HOVERED_BUTTON_STYLE));
        submit.setOnMouseExited(e -> submit.setStyle(IDLE_BUTTON_STYLE));

        displayCoins(coins);
    }
    public void goBack(ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }

    public void handleSubmit(ActionEvent event){
        System.out.println("clicked");
    }
}
