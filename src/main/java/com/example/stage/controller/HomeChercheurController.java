package com.example.stage.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeChercheurController {

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
    private Button changeThemeButton;

    @FXML
    private BorderPane mainPane;

    // Ajoutez ces champs pour les utiliser dans initializeData
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

    @FXML
    private void handleEditProfile() {
        try {
            // Charger la page de modification de profil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/editprofile.fxml"));
            Parent editProfilePage = loader.load();

            // Récupérer le contrôleur de la page de modification de profil
            //EditProfileController editProfileController = loader.getController();



            // Créer la scène
            Scene editProfileScene = new Scene(editProfilePage, 800, 600);

            // Récupérer la fenêtre actuelle
            Stage currentStage = (Stage) editProfileButton.getScene().getWindow();

            // Changer la scène
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