package client.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import protocol.Response;
import protocol.requests.GameRequest;
import protocol.requests.StartGameRequest;
import server.games.CoinFlip;
import server.games.GameType;

import java.io.IOException;
import java.util.Arrays;

public class CoinflipUIController extends UIController {
    public Button back;
    public Button heads;
    public Button tails;

    public void handleButtonAction (ActionEvent event) throws IOException {
        StartGameRequest startGameRequest = new StartGameRequest(GameType.COINFLIP);
        getServerCommunicator().sendRequest(startGameRequest);
        //TODO: Server communication so player plays.
        int side = ((Button) event.getTarget()).getId().equals("heads") ? CoinFlip.Sides.HEADS.getValue() : CoinFlip.Sides.TAILS.getValue();

        GameRequest gameRequest = new GameRequest(new int[]{side});
        Response response = getServerCommunicator().sendRequest(gameRequest);
        System.out.println("Chosen side: " + side + " received data: " + Arrays.toString(response.data));
    }
    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }
}
