package client.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import protocol.MessageType;
import protocol.Response;
import protocol.requests.TestRequest;

import java.io.IOException;

public class WheelUIController extends UIController {
    public Label errorlabel;
    public Button back;
    public TextField txtfield;
    public Button play;
    public void handleButtonAction (ActionEvent event) throws IOException {
        try {
            Float sum = Float.parseFloat(txtfield.getCharacters().toString());
            //TODO: Server communication so player plays.
            TestRequest testRequest = new TestRequest(MessageType.TEST);
            Response response = getServerCommunicator().sendRequest(testRequest);
            System.out.println(response.message);
            //sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
        }
        catch (NumberFormatException e ){
            errorlabel.setVisible(true);
        }
    }
    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }
}
