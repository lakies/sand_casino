package client.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Label victory;
    public Label loss;
    public Button res;
    private int vic;


    public void handleButtonAction (ActionEvent event) throws IOException {

        StartGameRequest startGameRequest = new StartGameRequest(GameType.COINFLIP);
        getServerCommunicator().sendRequest(startGameRequest);
        //TODO: Server communication so player plays.
        int side = ((Button) event.getTarget()).getId().equals("heads") ? CoinFlip.Sides.HEADS.getValue() : CoinFlip.Sides.TAILS.getValue();

        GameRequest gameRequest = new GameRequest(new int[]{side});
        Response response = getServerCommunicator().sendRequest(gameRequest);
        System.out.println("Chosen side: " + side + " received data: " + Arrays.toString(response.data));
        vic = response.data[0];
        back.setVisible(false);
        heads.setVisible(false);
        tails.setVisible(false);
        res.setVisible(true);


    }
    public void results(ActionEvent event){
        res.setVisible(false);
        if (vic == 1){
            victory.setVisible(true);
        } else{
            loss.setVisible(true);
        }
        back.setVisible(true);

    }

    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }
}
