package com.example.stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CVBuilder extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/stage/welcome.fxml"));
        primaryStage.setTitle("CV Builder");
        primaryStage.setScene(new Scene(root, 600, 900));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}