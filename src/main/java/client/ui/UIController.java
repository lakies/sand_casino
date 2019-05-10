package client.ui;

import client.ServerCommunicator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import protocol.MessageType;
import protocol.Response;
import protocol.requests.UserDataRequest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class UIController {
    private CountDownLatch serverReady = new CountDownLatch(1);
    private ServerCommunicator serverCommunicator;

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
            System.out.println("Requesting coins");
            try {
                getServerReady().await();
                Response response = getServerCommunicator().sendRequest(coinRequest);
                target.setText(Integer.toString(response.data[0]));
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

    public void setVisibleTimeout(Node targetNode){
        new Thread(() -> {
            targetNode.setVisible(true);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }

            targetNode.setVisible(false);
        }).start();
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
        stage.show();
    }
}
