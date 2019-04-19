package client.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class GameChoiceController extends UIController{

    public Button coin;
    public Button lottery;
    public Button poker;
    public Button wheel;

    public void handleButtonActionLottery (ActionEvent event) throws IOException {
    }

    public void handleButtonActionWheelOfFortune (ActionEvent event) throws IOException {
        sceneTransition("/WheelUI.fxml", wheel);
    }

    public void handleButtonAction (ActionEvent event) throws IOException {
    }

    public void handleButtonActionCoin (ActionEvent event) throws IOException {
        sceneTransition("/CoinflipUI.fxml", coin);
    }
}
