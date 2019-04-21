package client.ui;

import client.LoginHandler;
import client.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LogInController extends UIController {

    public TextField username;
    public TextField password;
    public Button logIn;
    public Button signUp;
    public Label loginFailed;

    public void logInButtonClicked() {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();

        try {
            User user = LoginHandler.login(enteredUsername, enteredPassword);
            if (user == null) {
                loginFailed.setText("Incorrect username or password.");
                loginFailed.setVisible(true);
            } else {
                sceneTransition("/gameChoiceScreen.fxml", logIn, user.getServerCommunicator());
            }
        } catch (IOException e) {
            loginFailed.setText("Could not connect to server.");
            loginFailed.setVisible(true);
        }
    }

    public void signUpButtonClicked() throws IOException {
        sceneTransition("/signUpScreen.fxml", signUp);
    }

}
