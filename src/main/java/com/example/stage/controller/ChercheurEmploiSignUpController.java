package com.example.stage.controller;

import com.example.stage.model.ChercheurEmploi;
import com.example.stage.service.ChercheurEmploiService;
import com.example.stage.util.BCryptUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class ChercheurEmploiSignUpController {
    @FXML
    private TextField usernameField; // Username field
    @FXML
    private TextField emailField; // Email field
    @FXML
    private PasswordField motdepasseField; // Password field
    @FXML
    private Label messageLabel; // Message label for feedback

    private ChercheurEmploiService chercheurEmploiService = new ChercheurEmploiService();

    @FXML
    public void sInscrire() {
        String username = usernameField.getText(); // Get username
        String email = emailField.getText(); // Get email
        String motdepasse = motdepasseField.getText(); // Get password

        // Validate required fields
        if (username.isEmpty() || email.isEmpty() || motdepasse.isEmpty()) {
            messageLabel.setText("Tous les champs obligatoires ne sont pas remplis.");
            return;
        }
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            messageLabel.setText("Please enter a valid email address.");
            return;
        }

        // Hash the password
        String hashedMotdepasse = BCryptUtil.hashPassword(motdepasse);

        // Create a ChercheurEmploi object with only the necessary fields
        ChercheurEmploi chercheurEmploi = new ChercheurEmploi(username, email, hashedMotdepasse, null, "chercheur_emploi");

        // Save the user to the database
        if (chercheurEmploiService.creerChercheurEmploi(chercheurEmploi)) {
            messageLabel.setText("Inscription réussie en tant que chercheur emploi !");
            redirectToHomePage(); // Redirect to the home page after successful registration
        } else {
            messageLabel.setText("Échec de l'inscription.");
        }
    }

    // Redirect to the home page
    private void redirectToHomePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/homechercheur.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 600);

            // Pass the connected user to the HomeController
            HomeController homeController = loader.getController();
            homeController.setUtilisateurConnecte(new ChercheurEmploi(usernameField.getText(), emailField.getText(), null, null, "chercheur_emploi"));

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers la page d'accueil.");
        }
    }
    // Redirect to the sign-up choice page
    @FXML
    public void retournerAuChoixInscription() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/sign-up-choice.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(loader.load(), 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers le choix d'inscription.");
        }
    }
}