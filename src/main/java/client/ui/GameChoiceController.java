package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameChoiceController extends UIController{

    public Button coin;
    public Button lottery;
    public Button poker;
    public Button wheel;

        public void handleButtonActionLottery (ActionEvent event) throws IOException {
        }

        public void handleButtonActionWheelOfFortune (ActionEvent event) throws IOException {
            Stage stage = (Stage) wheel.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/WheelUI.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }

        public void handleButtonAction (ActionEvent event) throws IOException {
        }

        public void handleButtonActionCoin (ActionEvent event) throws IOException {
            sceneTransition("/CoinflipUI.fxml", coin);
        }
}
