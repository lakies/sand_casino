package client.ui;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import protocol.GameType;
import protocol.Response;
import protocol.requests.GameRequest;
import protocol.requests.StartGameRequest;

import java.io.IOException;
import java.net.URL;
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
    public Label totalBetAmount;
    public ImageView playerCoins;
    public ImageView movingCoin;
    public Pane wheel;
    public Button freeSpin;

    private boolean startSlowing = false;
    private int stoppingIndex = 0;
    private AnimationTimer wheelAnimation;
    private int wonCoins = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ensureNumericOnly(txtfield);
        displayCoins(coins);

        wheelAnimation = new AnimationTimer() {
            double speed = 200;
            double rotation = 0;
            double lastTime = 0;
            boolean slowing = false;

            @Override
            public void handle(long l) {

                rotation = (rotation + (speed * (l - lastTime) / 1000000000.0)) % 360;

                if (startSlowing && !slowing && (int) ((rotation + 15) / 30) == (12 + stoppingIndex - 1) % 12) {
                    slowing = true;
                }

                if (slowing) {
                    speed -= 5 * (l - lastTime) / 100000000.0;
//                    System.out.println(speed);
                }
                wheel.setRotate(rotation);
                lastTime = l;

                if (speed < 5){
                    speed = 200;
                    slowing = false;
                    slowingFinished();
                    stop();
                }
            }
        };

        wheelAnimation.start();
    }

    public void freeSpin(ActionEvent event) throws IOException {
        try {
            StartGameRequest startGameRequest = new StartGameRequest(GameType.WHEEL);
            getServerCommunicator().sendRequest(startGameRequest);
            GameRequest gameRequest = new GameRequest(new int[]{50});
            gameRequest.setRequestType(GameRequest.GameRequestType.WHEEL_FREE);
            Response response = getServerCommunicator().sendRequest(gameRequest);
            if (response.getStatusCode() == Response.StatusCodes.TIME_ERROR) {
                errorlabel.setText("30 minutes hasn't passed since last free spin");
                setVisibleTimeout(errorlabel);
                return;
            }
            stoppingIndex = response.data[1];
            wonCoins = response.data[0];
            startSlowing = true;
            displayCoins(coins);
            coins.setText(Integer.toString(Integer.parseInt(coins.getText()) - wonCoins));
        }catch (NumberFormatException e ){
            errorlabel.setVisible(true);
        } catch (IOException e) {
            sceneTransition("/ConnectionLost.fxml", back);
        }
    }

    public void handleButtonAction(ActionEvent event) throws IOException {
        try {
            int sum = Integer.parseInt(txtfield.getCharacters().toString());


            StartGameRequest startGameRequest = new StartGameRequest(GameType.WHEEL);

            getServerCommunicator().sendRequest(startGameRequest);
            GameRequest gameRequest = new GameRequest(new int[]{sum});
            gameRequest.setRequestType(GameRequest.GameRequestType.WHEEL_PAID);


            Response response = getServerCommunicator().sendRequest(gameRequest);
            if (response.getStatusCode() == Response.StatusCodes.ERR_NOT_ENOUGH_FUNDS) {
                errorlabel.setText("You don't have " + sum + " coins to play");
                setVisibleTimeout(errorlabel);
                return;
            }


            wonCoins = response.data[0];
            stoppingIndex = response.data[1];

            coins.setText(Integer.toString(Integer.parseInt(coins.getText()) - sum));

            totalBetAmount.setText(Integer.toString(sum));


            startSlowing = true;


        } catch (NumberFormatException e) {
            errorlabel.setText("Please enter a value");
            errorlabel.setVisible(true);
        } catch (IOException e) {
            sceneTransition("/ConnectionLost.fxml", back);
        }
    }

    private void slowingFinished(){
        startSlowing = false;
        totalBetAmount.setText("0");

        if (wonCoins == 0){
            wheelAnimation.start();
            return;
        }

        moveCoin(movingCoin, playerCoins, () -> {
            coins.setText(Integer.toString(Integer.parseInt(coins.getText()) + wonCoins));
            wheelAnimation.start();
        });
    }

    public void handleResults(ActionEvent event) throws IOException{
        results.setVisible(false);
        lab1.setVisible(true);
        back.setVisible(true);

        amount.setVisible(true);
        displayCoins(coins);

    }

    public void goBack(ActionEvent event) throws IOException {
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }

    public void handleLogout(ActionEvent event) throws IOException {
        sceneTransition("/logInScreen.fxml", logout);
    }
}
