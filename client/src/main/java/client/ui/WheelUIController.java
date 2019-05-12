package client.ui;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import protocol.Response;
import protocol.requests.GameRequest;
import protocol.requests.StartGameRequest;
import protocol.GameType;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class WheelUIController extends UIController implements Initializable {
    public Label lab1;
    public Label errorlabel;
    public Button back;
    public TextField txtfield;
    public Button play;
    public Button results;
    public Button logout;
    public Label amount;
    public Label coins;

    private String coinAmount;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ensureNumericOnly(txtfield);
        displayCoins(coins);
    }

    public void handleButtonAction (ActionEvent event) throws IOException {
        try {
            int sum = Integer.parseInt(txtfield.getCharacters().toString());
            StartGameRequest startGameRequest = new StartGameRequest(GameType.WHEEL);
            getServerCommunicator().sendRequest(startGameRequest);
            GameRequest gameRequest = new GameRequest(new int[]{sum});

            Response response = getServerCommunicator().sendRequest(gameRequest);
            if (response.getStatusCode() == Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS){
                errorlabel.setText("You don't have " + sum + " coins to play");
                setVisibleTimeout(errorlabel);
                return;
            }
            errorlabel.setVisible(false);
            back.setVisible(false);
            results.setVisible(true);
            play.setVisible(false);
            txtfield.setVisible(false);
            coinAmount = Integer.toString(response.data[0]);
            System.out.println(coins);


            System.out.println(Arrays.toString(response.data));

            //sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
        }
        catch (NumberFormatException e ){
            errorlabel.setVisible(true);
        } catch (IOException e){
            sceneTransition("/ConnectionLost.fxml", back);
        }
    }
    public void handleResults(ActionEvent event) throws IOException{
        results.setVisible(false);
        lab1.setVisible(true);
        back.setVisible(true);

        amount.setText(coinAmount);
        amount.setVisible(true);
        displayCoins(coins);

    }
    public void goBack (ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }

    public void handleLogout(ActionEvent event) throws IOException{
        sceneTransition("/logInScreen.fxml", logout);
    }
}
