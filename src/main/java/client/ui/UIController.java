package client.ui;

import client.ServerCommunicator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIController {
    private ServerCommunicator serverCommunicator;

    public ServerCommunicator getServerCommunicator() {
        return serverCommunicator;
    }

    public void setServerCommunicator(ServerCommunicator serverCommunicator) {
        this.serverCommunicator = serverCommunicator;
    }

    public void sceneTransition(String resourceName, Node targetNode) throws IOException {
        Stage stage = (Stage) targetNode.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(resourceName));
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void sceneTransition(String resourceName, Node targetNode, ServerCommunicator communicator) throws IOException {
        Stage stage = (Stage) targetNode.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource(resourceName).openStream());
        UIController controller = fxmlLoader.getController();
        controller.setServerCommunicator(communicator);
        System.out.println("transition");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
