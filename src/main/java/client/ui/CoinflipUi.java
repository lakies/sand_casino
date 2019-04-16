package Client.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CoinflipUi extends Application {
    public static void main(String[] args) {
        launch(args);
    } //For testing purposes


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane border = new BorderPane();

        Button emptybutton = new Button("Play Coinflip");
        //emptybutton.setOnAction(me -> {


        //});
        BorderPane border2 = new BorderPane();
        border2.setLeft(emptybutton);
        border.setBottom(border2);


        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(border);
        Scene stseen1 = new Scene(flow, 300, 150, Color.SNOW);
        stage.setTitle("COINFLIP");
        stage.setResizable(false);
        stage.setScene(stseen1);
        stage.show();
    }
}
