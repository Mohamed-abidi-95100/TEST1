package com.example.stage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminGestionUtilisateursController {
    @FXML
    private Button gererEntreprisesButton;
    @FXML
    private Button gererChercheursEmploiButton;
    @FXML
    private Button retourButton;
    @FXML
    private Button voirToutesLesEntreprisesButton;

    // Open the "Manage All Enterprises" page
    @FXML
    public void ouvrirPageToutesEntreprises() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/admin-toutes-entreprises.fxml"));
            Stage stage = (Stage) voirToutesLesEntreprisesButton.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Open the "Manage All Job Seekers" page
    @FXML
    public void ouvrirPageTousChercheursEmploi() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/admin-tous-chercheurs-emploi.fxml"));
            Stage stage = (Stage) gererChercheursEmploiButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Return to the admin home page
    @FXML
    public void retournerAAdminHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/admin-home.fxml"));
            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}