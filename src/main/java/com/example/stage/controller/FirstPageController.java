package com.example.stage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class FirstPageController {
    @FXML
    private Button loginButton;
    @FXML
    private Button signUpButton;

    // Redirect to the login page
    @FXML
    public void redirectToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/login.fxml"));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Redirect to the sign-up choice page
    @FXML
    public void redirectToSignUpChoicePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/sign-up-choice.fxml"));
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}