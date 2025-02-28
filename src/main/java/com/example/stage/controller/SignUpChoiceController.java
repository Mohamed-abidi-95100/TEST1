package com.example.stage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class SignUpChoiceController {
    @FXML
    private Button chercheurEmploiButton;
    @FXML
    private Button entrepriseButton;
    @FXML
    private Button retourButton; // New button for going back to the first page


    @FXML
    public void redirectToChercheurEmploiSignUpPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/chercheur-emploi-sign-up.fxml"));
            Stage stage = (Stage) chercheurEmploiButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Redirect to the enterprise sign-up page
    @FXML
    public void redirectToEntrepriseSignUpPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/entreprise-sign-up.fxml"));
            Stage stage = (Stage) entrepriseButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Redirect to the first page
    @FXML
    public void redirectToFirstPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/first-page.fxml"));
            Stage stage = (Stage) retourButton.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(loader.load(), 800, 600)); // Load the first page
            stage.show(); // Show the new scene
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}