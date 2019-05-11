package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import protocol.Response;
import protocol.requests.GameRequest;
import protocol.requests.StartGameRequest;
import server.games.CoinFlip;
import protocol.GameType;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CoinflipUIController extends UIController implements Initializable {
    public Button back;
    public Button heads;
    public Button tails;
    public Label victory;
    public Label loss;
    public Label coins;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayCoins(coins);
    }

    public void handleButtonAction (ActionEvent event) throws IOException{

        StartGameRequest startGameRequest = new StartGameRequest(GameType.COINFLIP);
        try {
            getServerCommunicator().sendRequest(startGameRequest);
            int side = ((Button) event.getTarget()).getId().equals("heads") ? CoinFlip.Sides.HEADS.getValue() : CoinFlip.Sides.TAILS.getValue();

            GameRequest gameRequest = new GameRequest(new int[]{side});
            Response response = getServerCommunicator().sendRequest(gameRequest);
            System.out.println("Chosen side: " + side + " received data: " + Arrays.toString(response.data));

            if (response.data[0] == 0){
                setVisibleTimeout(loss);
            } else {
                setVisibleTimeout(victory);
            }
            displayCoins(coins);
        } catch (IOException e) {
            sceneTransition("/ConnectionLost.fxml", back);
        }
    }

    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }
}
