package client.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import protocol.Response;
import protocol.requests.GameRequest;
import protocol.requests.StartGameRequest;
import protocol.GameType;
import server.games.Lottery;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class LotteryUIController extends UIController implements Initializable {
    public Button submit;
    public Label coins;
    public Button back;
    public Label playerBetAmount;
    public Label totalBetAmount;
    public Label timeLeft;
    public TextField betAmount;
    public ProgressBar gameProgress;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #4eb5f1;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #4eb5f1; -fx-effect:  dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        submit.setStyle(IDLE_BUTTON_STYLE);
        submit.setOnMouseEntered(e -> submit.setStyle(HOVERED_BUTTON_STYLE));
        submit.setOnMouseExited(e -> submit.setStyle(IDLE_BUTTON_STYLE));

        System.out.println("Init called");
        ensureNumericOnly(betAmount);

        displayCoins(coins);

        startQuery();
    }

    public LotteryUIController() {

    }

    private void startQuery(){
        new Thread(() -> {
            try {
                getServerReady().await();
                StartGameRequest startGameRequest = new StartGameRequest(GameType.LOTTERY);
                getServerCommunicator().sendRequest(startGameRequest);

                GameRequest gameRequest = new GameRequest(new int[]{0});
                gameRequest.setRequestType(GameRequest.GameRequestType.LOTTERY_ADD_BET);
                getServerCommunicator().sendRequest(gameRequest);

                while (true){
                    if (isWindowClosed()){
                        break;
                    }
                    gameRequest = new GameRequest(new int[]{});
                    gameRequest.setRequestType(GameRequest.GameRequestType.LOTTERY_PROGRESS_QUERY);
                    Response response = getServerCommunicator().sendRequest(gameRequest);

                    if (response.data == null){
                        // TODO: show game over message
                        System.out.println("Ended");
                        break;
                    }

                    displayCoins(coins);

                    int[] data = response.data;

                    if (data[0] == 1){
                        if (data[1] == 1){
                            // TODO: show win message
                            System.out.println("Win");
                        } else {
                            // TODO: show lose message
                            System.out.println("Lose");
                        }
                        break;
                    }

                    Platform.runLater(() -> {
                        playerBetAmount.setText(Integer.toString(data[2]));
                        totalBetAmount.setText(Integer.toString(data[3]));

                        Duration remaining = Duration.between(LocalDateTime.now(), response.time.plusSeconds(Lottery.gameLength));
                        timeLeft.setText(remaining.getSeconds() / 60 + ":" + (remaining.getSeconds() % 60 + 1));

                        gameProgress.setProgress((Lottery.gameLength - remaining.getSeconds() - 1) / (Lottery.gameLength * 1.0));
                    });

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (IOException e){
                // TODO: handle server loss
                Platform.runLater(() -> {
                    try {
                        sceneTransition("/logInScreen.fxml", back);
                    } catch (IOException e1) {
                        throw new RuntimeException(e1);
                    }
                });
            }
        }).start();
    }
    public void goBack(ActionEvent event) throws IOException {
        setWindowClosed(null);
        sceneTransition("/gameChoiceScreen.fxml", back, getServerCommunicator());
    }

    public void handleSubmit(ActionEvent event) throws IOException{
        GameRequest gameRequest = new GameRequest(new int[]{Integer.parseInt(betAmount.getText())});
        gameRequest.setRequestType(GameRequest.GameRequestType.LOTTERY_ADD_BET);
        getServerCommunicator().sendRequest(gameRequest);
    }
}
