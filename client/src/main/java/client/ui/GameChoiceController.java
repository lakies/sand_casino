package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameChoiceController extends UIController implements Initializable {

    public Button coin;
    public Button lottery;
    public Button wheel;
    public Button logout;
    public Label coins;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayCoins(coins);
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

    public void handleLogout(ActionEvent event) throws IOException{
        sceneTransition("/logInScreen.fxml", logout);
    }
}
