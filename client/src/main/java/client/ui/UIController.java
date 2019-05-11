package client.ui;

import client.ServerCommunicator;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import protocol.MessageType;
import protocol.Response;
import protocol.requests.UserDataRequest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class UIController {
    private CountDownLatch serverReady = new CountDownLatch(1);
    private ServerCommunicator serverCommunicator;
    private boolean windowClosed = false;

    public boolean isWindowClosed() {
        return windowClosed;
    }

    public void setWindowClosed(WindowEvent event) {
        windowClosed = true;
    }

    public ServerCommunicator getServerCommunicator() {
        return serverCommunicator;
    }

    public void setServerCommunicator(ServerCommunicator serverCommunicator) {
        this.serverCommunicator = serverCommunicator;
        serverReady.countDown();
    }

    public CountDownLatch getServerReady() {
        return serverReady;
    }

    public void displayCoins(Label target){
        new Thread(() -> {
            UserDataRequest coinRequest = new UserDataRequest(MessageType.COIN_AMOUNT);
            try {
                getServerReady().await();
                Response response = getServerCommunicator().sendRequest(coinRequest);

                Platform.runLater(() -> {
                    target.setText(Integer.toString(response.data[0]));
                });
            } catch (IOException e){
                try {
                    sceneTransition("/ConnectionLost.fxml", target);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }).start();
    }

    public CountDownLatch setVisibleTimeout(Node targetNode, int timeout){
        CountDownLatch latch = new CountDownLatch(1);
        new Thread(() -> {
            Platform.runLater(() -> {
                targetNode.setVisible(true);
            });
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }

            Platform.runLater(() -> {
                targetNode.setVisible(false);
            });

            latch.countDown();
        }).start();

        return latch;
    }

    public void setVisibleTimeout(Node targetNode){
        setVisibleTimeout(targetNode, 1000);
    }

    public void setVisibleTimeout(Node targetNode, int timeout, Runnable after){
        try {
            setVisibleTimeout(targetNode, timeout).await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        Platform.runLater(after);
    }

    public void ensureNumericOnly(TextField tf){
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public void sceneTransition(String resourceName, Node targetNode) throws IOException {
        Stage stage = (Stage) targetNode.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(resourceName));
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void sceneTransition(String resourceName, Node targetNode, ServerCommunicator communicator) throws IOException {
        Stage stage = (Stage) targetNode.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resourceName));
        Parent root = fxmlLoader.load();
        UIController controller = fxmlLoader.getController();
        controller.setServerCommunicator(communicator);
        stage.setScene(new Scene(root));
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, controller::setWindowClosed);
        stage.show();
    }
}
