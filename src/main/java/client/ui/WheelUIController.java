package Client.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class WheelUIController {
    public Label errorlabel;
    public Button back;
    public TextField txtfield;
    public void handleButtonAction (ActionEvent event) throws IOException {
        try {
            Float sum = Float.parseFloat(txtfield.getCharacters().toString());
            //TODO: Server communication so player plays.
        }
        catch (NumberFormatException e ){
            errorlabel.setVisible(true);
        }
    }
    public void goBack (ActionEvent event) throws IOException {
        Stage stage = (Stage) back.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/gameChoiceScreen.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
