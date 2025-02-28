package com.example.stage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class AdminHomeController {
    @FXML
    private Button gererDemandesButton;

    // Open the "Manage Enterprise Requests" page
    @FXML
    public void ouvrirPageDemandesEntreprises() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/admin-demandes.fxml"));
            Stage stage = (Stage) gererDemandesButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Open the "Manage Users" page (optional)
    @FXML
    public void ouvrirPageGestionUtilisateurs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/admin-gestion-utilisateurs.fxml"));
            Stage stage = (Stage) gererDemandesButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Logout functionality
    @FXML
    public void deconnecter() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/login.fxml"));
            Stage stage = (Stage) gererDemandesButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}