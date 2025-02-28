package com.example.stage.controller;

import com.example.stage.model.Administrateur;
import com.example.stage.service.AdministrateurService;
import com.example.stage.util.BCryptUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAdminController {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField motdepasseField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField nomCompletField;
    @FXML
    private Label messageLabel;

    private AdministrateurService administrateurService = new AdministrateurService();

    @FXML
    public void creerAdministrateur() {
        String username = usernameField.getText(); // Use 'username' instead of 'nom'
        String email = emailField.getText();
        String motdepasse = motdepasseField.getText();
        String prenom = prenomField.getText();
        String nomComplet = nomCompletField.getText();

        if (username.isEmpty() || email.isEmpty() || motdepasse.isEmpty() || prenom.isEmpty() || nomComplet.isEmpty()) {
            messageLabel.setText("Tous les champs sont obligatoires.");
            return;
        }

        String hashedMotdepasse = BCryptUtil.hashPassword(motdepasse);
        Administrateur administrateur = new Administrateur(username, email, hashedMotdepasse, null, prenom, nomComplet);

        if (administrateurService.creerAdministrateur(administrateur)) {
            messageLabel.setText("Administrateur créé avec succès !");
        } else {
            messageLabel.setText("Échec de la création de l'administrateur.");
        }
    }


    private void redirectToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/login.fxml"));
            Scene scene = new Scene(loader.load(), 800, 600);
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            messageLabel.setText("Erreur lors de la redirection vers la page de connexion.");
        }
    }
}