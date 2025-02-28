package com.example.stage.controller;

import com.example.stage.model.ChercheurEmploi;
import com.example.stage.model.Entreprise;
import com.example.stage.service.ChercheurEmploiService;
import com.example.stage.service.EntrepriseService;
import com.example.stage.util.BCryptUtil; // Ajoutez cette importation
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField usernameField; // Use 'usernameField' instead of 'nomField'
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField motdepasseField;
    @FXML
    private ComboBox<String> roleComboBox; // Options: "chercheur_emploi", "entreprise"
    @FXML
    private Label messageLabel;

    private ChercheurEmploiService chercheurEmploiService = new ChercheurEmploiService();
    private EntrepriseService entrepriseService = new EntrepriseService();

    // Méthode appelée lors du clic sur le bouton "S'inscrire"
    @FXML
    public void sInscrire() {
        String username = usernameField.getText(); // Use 'username' instead of 'nom'
        String email = emailField.getText();
        String motdepasse = motdepasseField.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || email.isEmpty() || motdepasse.isEmpty() || role == null) { // Check 'username' instead of 'nom'
            messageLabel.setText("Tous les champs sont obligatoires.");
            return;
        }

        // Hacher le mot de passe avant de créer l'utilisateur
        String hashedMotdepasse = BCryptUtil.hashPassword(motdepasse);

        switch (role) {
            case "chercheur_emploi":
                // Create a job seeker using 'username' instead of 'nom'
                ChercheurEmploi chercheurEmploi = new ChercheurEmploi(username, email, hashedMotdepasse, null, null, null, null);
                if (chercheurEmploiService.creerChercheurEmploi(chercheurEmploi)) {
                    messageLabel.setText("Inscription réussie en tant que chercheur d'emploi !");
                    redirectToLoginPage(); // Redirection vers la page de connexion
                } else {
                    messageLabel.setText("Échec de l'inscription.");
                }
                break;

            case "entreprise":
                // Create an enterprise using 'username' as 'nom_entreprise'
                Entreprise entreprise = new Entreprise(username, email, hashedMotdepasse, null, username, null, null, null, null, null);
                if (entrepriseService.creerEntrepriseEnAttente(entreprise)) {
                    messageLabel.setText("Votre compte entreprise est en attente de validation par un administrateur.");
                    redirectToLoginPage();
                } else {
                    messageLabel.setText("Échec de l'inscription.");
                }
                break;

            default:
                messageLabel.setText("Rôle invalide.");
        }
    }

    // Redirection vers la page de connexion
    private void redirectToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow(); // Use 'usernameField' instead of 'nomField'
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers la page de connexion.");
        }
    }
}