package com.example.stage.controller;

import com.example.stage.model.Utilisateur;
import com.example.stage.service.UtilisateurService;
import com.example.stage.util.BCryptUtil; // Ajoutez cette importation
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField motdepasseField;
    @FXML
    private Label messageLabel;
    @FXML
    private Hyperlink inscriptionLink;

    private UtilisateurService utilisateurService = new UtilisateurService();

    /**
     * Called when the FXML file is loaded.
     * Checks if an admin exists and redirects to the create-admin page if not.
     */
    @FXML
    private void initialize() {
        // Check if any admin exists in the database
        if (!utilisateurService.existsAdmin()) {
            // Schedule the redirection after the FXML is fully loaded
            Platform.runLater(this::redirectToCreateAdminPage);
        }
    }

    /**
     * Redirects to the create-admin page if no admin exists.
     */
    private void redirectToCreateAdminPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/create-admin.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);

            // Get the current stage from the emailField's scene (if available)
            Stage stage;
            if (emailField != null && emailField.getScene() != null) {
                stage = (Stage) emailField.getScene().getWindow();
            } else {
                // Fallback: Create a new stage if no scene is available yet
                stage = new Stage();
            }

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers la page de création d'admin.");
        }
    }

    /**
     * Handles the login process when the "Se Connecter" button is clicked.
     */
    @FXML
    public void seConnecter() {
        String email = emailField.getText();
        String motdepasse = motdepasseField.getText();

        // Validate required fields
        if (email.isEmpty() || motdepasse.isEmpty()) {
            messageLabel.setText("Tous les champs sont obligatoires.");
            return;
        }

        // Retrieve user by email
        Utilisateur utilisateur = utilisateurService.getUtilisateurByEmail(email);
        if (utilisateur != null && BCryptUtil.checkPassword(motdepasse, utilisateur.getMotdepasse())) {
            switch (utilisateur.getRole()) {
                case "administrateur":
                    redirectToAdminHomePage(); // Redirect to admin home page
                    break;
                case "chercheur_emploi":
                    redirectToHomePage(utilisateur); // Redirect to seeker home page
                    break;
                case "entreprise":
                    redirectToHomePage(utilisateur); // Redirect to enterprise home page
                    break;
                default:
                    messageLabel.setText("Rôle invalide.");
            }
        } else {
            messageLabel.setText("Email ou mot de passe incorrect.");
        }
    }

    /**
     * Redirects to the admin home page.
     */
    private void redirectToAdminHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/admin-home.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers la page d'accueil admin.");
        }
    }

    /**
     * Redirects to the home page for job seekers or enterprises.
     *
     * @param utilisateur The connected user.
     */
    private void redirectToHomePage(Utilisateur utilisateur) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/home.fxml"));
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.show();

            // Pass the connected user to the HomeController
            HomeController homeController = loader.getController();
            homeController.setUtilisateurConnecte(utilisateur);
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers la page d'accueil.");
        }
    }

    /**
     * Redirects to the sign-up choice page when the "S'inscrire" link is clicked.
     */
    public void allerVersInscription() {
        try {
            // Load the sign-up-choice.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/sign-up-choice.fxml"));
            Stage stage = (Stage) inscriptionLink.getScene().getWindow(); // Get the current stage
            stage.setScene(new Scene(loader.load(), 800, 600)); // Set the new scene
            stage.show(); // Display the new scene
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers la page d'inscription.");
        }
    }
}