package client.ui;

import client.LoginHandler;
import client.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUpController extends UIController {

    public TextField username;
    public TextField password1;
    public TextField password2;
    public Button signUp;
    public Button login;
    public Label invalidInput;

    public void signUpClicked() throws IOException {
        String enteredUsername = username.getText();
        String enteredPw1 = password1.getText();
        String enteredPw2 = password2.getText();

        if (enteredUsername.equals("") || enteredPw1.equals("") || enteredPw2.equals("")) {
            invalidInput.setText("Please fill all fields.");
        } else if (!enteredPw1.equals(enteredPw2)) {
            invalidInput.setText("Entered passwords do not match.");
        } else {
            try {
                User user = LoginHandler.createAccount(enteredUsername, enteredPw1);
                if (user == null) {
                    invalidInput.setText("Entered username already exists.");
                } else {
                    sceneTransition("/gameChoiceScreen.fxml", signUp, user.getServerCommunicator());
                }
            } catch (RuntimeException e) {
                invalidInput.setText("Account creation failed.");
            }
        }
    }

    public void loginClicked() throws IOException {
        sceneTransition("/logInScreen.fxml", login);
    }
}
