package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import protocol.GameType;
import protocol.Response;
import protocol.requests.GameRequest;
import protocol.requests.StartGameRequest;
import server.games.CoinFlip;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CoinflipUIController extends UIController implements Initializable {
    public Button back;
    public Button heads;
    public Button tails;
    public Label result;
    public Label coins;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayCoins(coins);
    }

    public void handleButtonAction (ActionEvent event) throws IOException{

        StartGameRequest startGameRequest = new StartGameRequest(GameType.COINFLIP);
        try {
            Response startGameResponse = getServerCommunicator().sendRequest(startGameRequest);

            if (startGameResponse.getStatusCode().equals(Response.StatusCodes.ERR_INVALID_CREDENTIALS)) {
                System.out.println("Failed to authenticate");
                result.setText("Authentication failed, please try again");
            } else if (startGameResponse.getStatusCode().equals(Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS)) {
                System.out.println("Insufficient funds");
                result.setText("You need at least 50 coins to play");
            } else {
                int side = ((Button) event.getTarget()).getId().equals("heads") ? CoinFlip.Sides.HEADS.getValue() : CoinFlip.Sides.TAILS.getValue();

                GameRequest gameRequest = new GameRequest(new int[]{side});
                Response response = getServerCommunicator().sendRequest(gameRequest);

                System.out.println("Chosen side: " + side + " received data: " + Arrays.toString(response.data));

                if (response.data[0] == 0){
                    result.setText("YOU LOST -50 coins");
                } else {
                    result.setText("YOU WON +50 coins");
                }
                displayCoins(coins);
            }
            setVisibleTimeout(result);
        } catch (IOException e) {
            sceneTransition("/ConnectionLost.fxml", back);
        }
    }

    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }
}
