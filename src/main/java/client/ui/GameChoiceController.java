package client.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import protocol.requests.StartGameRequest;
import server.games.GameType;

import java.io.IOException;

public class GameChoiceController extends UIController{

    public Button coin;
    public Button lottery;
    public Button poker;
    public Button wheel;

    public void handleButtonActionLottery (ActionEvent event) throws IOException {
        sceneTransition("/LotteryUI.fxml", wheel, getServerCommunicator());
    }

    public void handleButtonActionWheelOfFortune (ActionEvent event) throws IOException {
        sceneTransition("/WheelUI.fxml", wheel, getServerCommunicator());
    }

    public void handleButtonAction (ActionEvent event) throws IOException {
    }

    public void handleButtonActionCoin (ActionEvent event) throws IOException {
        sceneTransition("/CoinflipUI.fxml", coin, getServerCommunicator());
    }
}
