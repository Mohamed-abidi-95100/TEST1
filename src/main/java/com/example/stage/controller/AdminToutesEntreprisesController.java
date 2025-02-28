package com.example.stage.controller;

import com.example.stage.service.UtilisateurService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminToutesEntreprisesController {
    @FXML
    private ListView<String> entreprisesListView; // ListView to display all enterprises
    @FXML
    private Button retourButton; // Button to go back
    @FXML
    private Label messageLabel;

    private UtilisateurService utilisateurService = new UtilisateurService();

    // Load all enterprises when the page initializes
    @FXML
    public void initialize() {
        chargerListeEntreprises();
    }

    // Method to load all enterprises from the database
    private void chargerListeEntreprises() {
        try {
            List<String> entreprises = utilisateurService.getAllEntreprises(); // Fetch from the 'entreprises' table
            entreprisesListView.getItems().clear(); // Clear existing items
            entreprisesListView.getItems().addAll(entreprises); // Add fetched enterprises
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors du chargement des entreprises.");
        }
    }

    // Redirect to the previous page
    @FXML
    public void retournerAAdminHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/admin-home.fxml"));
            Stage stage = (Stage) retourButton.getScene().getWindow(); // Get the current stage
            Scene scene = new Scene(loader.load(), 800, 600); // Load the admin home page
            stage.setScene(scene); // Set the new scene
            stage.show(); // Show the new page
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers la page d'accueil administrateur.");
        }
    }
}