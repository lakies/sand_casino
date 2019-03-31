package Client.ui;

import Client.LoginHandler;
import Client.User;
import Client.UserHolder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {

    public TextField username;
    public TextField password;
    public Button logIn;
    public Button signUp;
    public Label loginFailed;

    public void logInButtonClicked() throws IOException {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        User user = LoginHandler.login(enteredUsername, enteredPassword);
        if (user == null) {
            loginFailed.setVisible(true);
        } else {
            UserHolder.authenticatedUser = user;
            Stage stage = (Stage) logIn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/gameChoiceScreen.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void signUpButtonClicked() throws IOException {
        Stage stage = (Stage) signUp.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/signUpScreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

}
