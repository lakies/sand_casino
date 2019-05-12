package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import protocol.GameType;
import protocol.Response;
import protocol.requests.GameRequest;
import protocol.requests.StartGameRequest;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class FreeSpinUIController extends UIController implements Initializable {
    public Label errorlabel;
    public Button play;
    public Label coins;
    public Button back;
    public Button logout;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayCoins(coins);
    }
    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }

    public void handleLogout(ActionEvent event) throws IOException{
        sceneTransition("/logInScreen.fxml", logout);
    }

    public void handleButtonAction (ActionEvent event) throws IOException {
        try {
            StartGameRequest startGameRequest = new StartGameRequest(GameType.WHEEL);
            getServerCommunicator().sendRequest(startGameRequest);
            GameRequest gameRequest = new GameRequest(new int[]{50});
            getServerCommunicator().sendRequest(gameRequest);

            Response response = getServerCommunicator().sendRequest(gameRequest);
            System.out.println(Arrays.toString(response.data));

        }
        catch (NumberFormatException e ){
            errorlabel.setVisible(true);
        } catch (IOException e){
            sceneTransition("/ConnectionLost.fxml", back);
        }
    }




}
