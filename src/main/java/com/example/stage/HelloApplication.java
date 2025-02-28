package com.example.stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the first page FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("first-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        // Remove window decorations (optional)
        stage.initStyle(StageStyle.UNDECORATED);

        // Set the scene and show the stage
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}