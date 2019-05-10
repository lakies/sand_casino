package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import protocol.MessageType;
import protocol.Response;
import protocol.requests.StartGameRequest;
import protocol.requests.UserDataRequest;
import server.games.GameType;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameChoiceController extends UIController implements Initializable {

    public Button coin;
    public Button lottery;
    public Button wheel;
    public Label coins;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new Thread(() -> {
            UserDataRequest coinRequest = new UserDataRequest(MessageType.COIN_AMOUNT);
            System.out.println("Requesting coins");
            try {
                getServerReady().await();
                Response response = getServerCommunicator().sendRequest(coinRequest);
                coins.setText(Integer.toString(response.data[0]));
            } catch (IOException e){
                // TODO: handle connection loss when already logged in.
                System.out.println("Server connection failed");
                throw new RuntimeException(e);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void handleButtonActionLottery (ActionEvent event) throws IOException {
        sceneTransition("/LotteryUI.fxml", wheel, getServerCommunicator());
    }

    public void handleButtonActionWheelOfFortune (ActionEvent event) throws IOException {
        sceneTransition("/WheelUI.fxml", wheel, getServerCommunicator());
    }

    public void handleButtonActionCoin (ActionEvent event) throws IOException {
        sceneTransition("/CoinflipUI.fxml", coin, getServerCommunicator());
    }
}
