package client.ui;

import client.LoginHandler;
import client.User;
import client.UserHolder;
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

    public void logInButtonClicked() throws IOException {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        User user = LoginHandler.login(enteredUsername, enteredPassword);
        // TODO: kui .login() throwib IOExceptioni siis ei saanud serveriga ühendust. Visata mingi punane tekst et a la connection to server failed.
        if (user == null) {
            loginFailed.setVisible(true);
        } else {
            UserHolder.authenticatedUser = user;
            sceneTransition("/gameChoiceScreen.fxml", logIn, user.getServerCommunicator());
        }
    }

    public void signUpButtonClicked() throws IOException {
        sceneTransition("/signUpScreen.fxml", signUp);
    }

}
