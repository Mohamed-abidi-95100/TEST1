package com.example.stage.controller;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeController {

    @FXML
    private ImageView loadingGear;

    @FXML
    private void initialize() {
        // Create a rotation animation for the loading gear
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), loadingGear);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.play();

        // Timer to transition to the next page after 3 seconds
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            private double progress = 0.0;

            @Override
            public void run() {
                progress += 0.01;

                if (progress >= 1.0) {
                    timer.cancel();
                    Platform.runLater(() -> showPage1());
                }
            }
        };
        timer.schedule(task, 0, 70);
    }

    private void showPage1() {
        Stage stage = (Stage) loadingGear.getScene().getWindow();
        try {
            AnchorPane page1 = FXMLLoader.load(getClass().getResource("/com/example/stage/page1.fxml"));
            Scene scene = new Scene(page1, 600, 900);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}