/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.ui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

/**
 *
 * @author nikoniem
 */
public class BetaGui extends Application {

    private boolean timerOn;

    @Override
    public void start(Stage window) throws Exception {

        Label l = new Label("Username");
        TextField t = new TextField();
        Button b = new Button("Login");

        GridPane layout1 = new GridPane();
        layout1.add(l, 0, 0);
        layout1.add(t, 0, 1);
        layout1.add(b, 0, 2);

        layout1.setPrefSize(300, 180);
        layout1.setAlignment(Pos.CENTER);
        layout1.setVgap(10);
        layout1.setHgap(10);
        layout1.setPadding(new Insets(20, 20, 20, 20));

        Scene loginScene = new Scene(layout1);

        // Stoptimer gui layout test1
        Label h = new Label("00:00:00");
        h.setFont(new Font(32.0));
        // h.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button stopButton = new Button("Stop");

        GridPane layout2 = new GridPane();
        layout2.add(h, 0, 0, 3, 1);
        GridPane.setHalignment(h, HPos.CENTER);
        // layout2.setHalignment(h, HPos.CENTER);
        layout2.add(startButton, 0, 1);
        layout2.add(pauseButton, 1, 1);
        layout2.add(stopButton, 2, 1);

        layout2.setPrefSize(300, 180);
        layout2.setAlignment(Pos.CENTER);
        layout2.setVgap(10);
        layout2.setHgap(10);
        layout2.setPadding(new Insets(20, 20, 20, 20));

        Scene timerScene = new Scene(layout2);

        startButton.setOnAction((event) -> {
            new AnimationTimer() {
                long pasttime = 0;
                int t = 0;

                @Override
                public void handle(long now) {
                    if (now - pasttime < 1000000000) {
                        return;
                    }

                    this.t++;
                    Double minutes = Math.floor(this.t / 60);
                    Double hours = Math.floor(minutes / 60);
                    minutes = minutes - (60 * hours);
                    Double seconds = this.t - (60 * minutes) - (60 * 60 * hours);

                    // Add leading zeros if needed...
                    String hoursFormat = hours.intValue() + "";
                    if (hoursFormat.length() == 1) {
                        hoursFormat = "0" + hoursFormat;
                    }

                    String minutesFormat = minutes.intValue() + "";
                    if (minutesFormat.length() == 1) {
                        minutesFormat = "0" + minutesFormat;
                    }

                    String secondsFormat = seconds.intValue() + "";
                    if (secondsFormat.length() == 1) {
                        secondsFormat = "0" + secondsFormat;
                    }

                    // System.out.println("sec");
                    h.setText(hoursFormat + ":" + minutesFormat + ":" + secondsFormat);

                    this.pasttime = now;
                }
            }.start();
        });

        b.setOnAction((event) -> {
            window.setScene(timerScene);
        });

        window.setTitle("StudyTimeTracker!");
        window.setScene(loginScene);
        window.show();
    }

    public static void main(String[] args) {
        launch(BetaGui.class);
    }

}
