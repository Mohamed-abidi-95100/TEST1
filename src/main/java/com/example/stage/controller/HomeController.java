package com.example.stage.controller;

import com.example.stage.model.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button editProfileButton;

    @FXML
    private Button createCVButton;

    @FXML
    private Button interviewButton;

    @FXML
    private Button communicationButton;

    @FXML
    private Button logoutButton;
    @FXML
    private Utilisateur utilisateurConnecte;


    @FXML
    private Button changeThemeButton;

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label bienvenueLabel; // Label pour afficher le message de bienvenue

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField addressField;

    // Méthode pour définir l'utilisateur connecté et afficher le message de bienvenue
    public void setUtilisateurConnecte(Utilisateur utilisateur) {
        if (utilisateur != null) {
            // Affiche "Bienvenue, [Username]"
            bienvenueLabel.setText("Bienvenue, " + utilisateur.getUsername() + " !");
        } else {
            bienvenueLabel.setText("Bienvenue !");
        }
    }


    @FXML
    private void handleEditProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/editprofile.fxml"));
            Parent editProfilePage = loader.load();

            // Récupérer le contrôleur et passer les informations de l'utilisateur connecté
            EditProfileController editProfileController = loader.getController();
            editProfileController.setUtilisateur(utilisateurConnecte);

            Scene editProfileScene = new Scene(editProfilePage, 800, 600);
            Stage currentStage = (Stage) editProfileButton.getScene().getWindow();
            currentStage.setScene(editProfileScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page de modification de profil : " + e.getMessage());
        }
    }


    @FXML
    private void handleCreateCV() {
        System.out.println("Créer CV button clicked");
        // Ajoutez votre logique ici
    }

    @FXML
    private void handleInterview() {
        System.out.println("Entretien button clicked");
        // Ajoutez votre logique ici
    }

    @FXML
    private void handleCommunication() {
        System.out.println("Communication button clicked");
        // Ajoutez votre logique ici
    }

    @FXML
    private void handleLogout() {
        try {
            // Charger la page d'inscription
            Parent signUpPage = FXMLLoader.load(getClass().getResource("/com/example/stage/first-page.fxml"));
            Scene signUpScene = new Scene(signUpPage, 800, 600);

            // Récupérer la fenêtre actuelle
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();

            // Changer la scène
            currentStage.setScene(signUpScene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement de la page d'inscription : " + e.getMessage());
        }
    }

    @FXML
    private void handleChangeTheme() {
        System.out.println("Changer Thème button clicked");
        // Exemple de changement de thème
        mainPane.setStyle("-fx-background-color: #333333;");
        editProfileButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
        createCVButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
        interviewButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
        communicationButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
        logoutButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white;");
    }
}