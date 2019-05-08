package client.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import protocol.MessageType;
import protocol.Response;
import protocol.requests.GameRequest;
import protocol.requests.StartGameRequest;
import protocol.requests.TestRequest;
import server.games.GameType;

import java.io.IOException;
import java.util.Arrays;

public class WheelUIController extends UIController {
    public Label lab1;
    public Label errorlabel;
    public Button back;
    public TextField txtfield;
    public Button play;
    public Button results;
    public Label amount;
    public Button back1;

    private String coins ="";



    public void handleButtonAction (ActionEvent event) throws IOException {
        try {
            int sum = Integer.parseInt(txtfield.getCharacters().toString());
            //TODO: Server communication so player plays.
            StartGameRequest startGameRequest = new StartGameRequest(GameType.WHEEL);
            getServerCommunicator().sendRequest(startGameRequest);
            GameRequest gameRequest = new GameRequest(new int[]{sum});

            Response response = getServerCommunicator().sendRequest(gameRequest);
            errorlabel.setVisible(false);
            back.setVisible(false);
            results.setVisible(true);
            play.setVisible(false);
            txtfield.setVisible(false);
            coins = response.data[0] + "";
            System.out.println(coins);


            System.out.println(Arrays.toString(response.data));

            //sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
        }
        catch (NumberFormatException e ){
            errorlabel.setVisible(true);
        }
    }
    public void handleResults(ActionEvent event) throws IOException{
        results.setVisible(false);
        lab1.setVisible(true);
        back.setVisible(true);

        amount.setText(coins);
        amount.setVisible(true);

    }
    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }
}
