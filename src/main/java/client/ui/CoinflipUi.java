package Client.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class CoinflipUi extends Application {
    public static void main(String[] args) {
        launch(args);
    } //For testing purposes

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane border = new BorderPane();

        Button emptybutton = new Button("Play Coinflip?");
        // küsimuse ja kahe nupu loomine
        Label label = new Label("Heads or Tails?");
        Button heads = new Button("Heads");
        Button tails = new Button("Tails");

        // sündmuse lisamine nupule heads
        heads.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            }
        });

        // sündmuse lisamine nupule tails
        tails.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            }
        });

        // nuppude grupeerimine
        FlowPane pane = new FlowPane(10, 10);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(heads, tails);

        // küsimuse ja nuppude gruppi paigutamine
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, pane);




        FlowPane flow = new FlowPane();
        flow.getChildren().addAll(border, vBox);
        Scene stseen1 = new Scene(flow, 300, 150, Color.SNOW);
        stage.setTitle("COINFLIP");
        stage.setResizable(false);
        stage.setScene(stseen1);
        stage.show();
    }
}
