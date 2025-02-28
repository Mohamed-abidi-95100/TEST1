package com.example.stage.controller;

import com.example.stage.dao.UtilisateurDAO;
import com.example.stage.model.ChercheurEmploi;
import com.example.stage.model.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EditProfileController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField diplomesField;

    private ChercheurEmploi utilisateur;

    public void setUtilisateur(Utilisateur utilisateur) {
        if (utilisateur instanceof ChercheurEmploi) {
            this.utilisateur = (ChercheurEmploi) utilisateur;
            usernameField.setText(this.utilisateur.getUsername());
            emailField.setText(this.utilisateur.getEmail());
            passwordField.setText(this.utilisateur.getMotdepasse());
            diplomesField.setText(this.utilisateur.getDiplomes());
        }
    }



    @FXML
    private void handleSaveProfile() {
        // Mettre à jour les informations de l'utilisateur
        utilisateur.setUsername(usernameField.getText());
        utilisateur.setEmail(emailField.getText());
        utilisateur.setMotdepasse(passwordField.getText());
        utilisateur.setDiplomes(diplomesField.getText());

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

        // Appeler la méthode appropriée en fonction du type de l'utilisateur
        boolean isUpdated = false;
        if (utilisateur instanceof ChercheurEmploi) {
            isUpdated = utilisateurDAO.mettreAJourUtilisateur((ChercheurEmploi) utilisateur);
        } else {
            isUpdated = utilisateurDAO.mettreAJourUtilisateur(utilisateur);
        }

        if (isUpdated) {
            System.out.println("Profil mis à jour avec succès !");
        } else {
            System.out.println("Erreur lors de la mise à jour du profil.");
        }

        // Retourner à la page d'accueil ou fermer la fenêtre
        handleCancel();
    }


    @FXML
    private void handleCancel() {
        // Fermer la fenêtre ou retourner à la page d'accueil
        usernameField.getScene().getWindow().hide();
    }
}