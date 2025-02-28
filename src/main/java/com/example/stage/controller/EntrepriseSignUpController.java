package com.example.stage.controller;

import com.example.stage.model.Entreprise;
import com.example.stage.service.EntrepriseService;
import com.example.stage.util.BCryptUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EntrepriseSignUpController {
    @FXML
    private TextField usernameField; // Updated field name
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField motdepasseField;
    @FXML
    private TextField nomEntrepriseField;
    @FXML
    private TextField secteurActiviteField;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField siteWebField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Label messageLabel;

    private EntrepriseService entrepriseService = new EntrepriseService();

    @FXML
    public void sInscrire() {
        String username = usernameField.getText(); // Use 'username' instead of 'nom'
        String email = emailField.getText();
        String motdepasse = motdepasseField.getText();
        String nomEntreprise = nomEntrepriseField.getText();
        String secteurActivite = secteurActiviteField.getText();
        String adresse = adresseField.getText();
        String siteWeb = siteWebField.getText();
        String description = descriptionField.getText();

        // Validate required fields
        if (username.isEmpty() || email.isEmpty() || motdepasse.isEmpty() || nomEntreprise.isEmpty()) {
            messageLabel.setText("Tous les champs obligatoires ne sont pas remplis.");
            return;
        }

        // Hash the password
        String hashedMotdepasse = BCryptUtil.hashPassword(motdepasse);

        // Create the enterprise object
        Entreprise entreprise = new Entreprise(username, email, hashedMotdepasse, null, nomEntreprise, secteurActivite, adresse, siteWeb, description, null); // Set logo to null

        // Save the enterprise to the database
        if (entrepriseService.creerEntrepriseEnAttente(entreprise)) {
            messageLabel.setText("Votre compte entreprise est en attente de validation par un administrateur.");
        } else {
            messageLabel.setText("Échec de l'inscription.");
        }
    }
    /**
     * Redirects the user back to the sign-up choice page.
     */
    @FXML
    public void retournerAuChoixInscription() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/sign-up-choice.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow(); // Get the current stage
            Scene scene = new Scene(loader.load(), 800, 600); // Load the sign-up choice page
            stage.setScene(scene); // Set the new scene
            stage.show(); // Show the new page
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers le choix d'inscription.");
        }
    }
}