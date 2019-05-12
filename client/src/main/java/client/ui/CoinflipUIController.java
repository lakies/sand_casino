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
    public Button logout;
    public Label result;
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

            if (response.getStatusCode() == Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS){
                result.setText("Not enough coins");
                setVisibleTimeout(result);
                return;
            }

            System.out.println("Chosen side: " + side + " received data: " + Arrays.toString(response.data));

            if (response.data[0] == 0){
                result.setText("YOU LOST -50 coins");
            } else {
                result.setText("YOU WON +50 coins");
            }
            displayCoins(coins);

            setVisibleTimeout(result);
        } catch (IOException e) {
            sceneTransition("/ConnectionLost.fxml", back);
        }
    }

    public void handleLogout(ActionEvent event) throws IOException{
        sceneTransition("/logInScreen.fxml", logout);
    }

    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }
}
