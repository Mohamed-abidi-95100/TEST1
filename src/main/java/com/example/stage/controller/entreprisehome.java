package com.example.stage.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class entreprisehome {

    @FXML
    private Label bienvenueLabel;

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
    private Button changeThemeButton;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button demandetButton;

    @FXML
    private Button recrutementButton;

    @FXML
    private Button logoutButton;

    @FXML
    private void handleChangeTheme() {
        // Logique pour changer le thème
        System.out.println("Changer le thème");
    }

    @FXML
    private void handleEditProfile() {
        // Logique pour modifier le profil
        System.out.println("Modifier le profil");
    }

    @FXML
    private void handledemande() {
        // Logique pour gérer les demandes
        System.out.println("Gérer les demandes");
    }

    @FXML
    private void handlerecrutement() {
        // Logique pour ajouter un recrutement
        System.out.println("Ajouter un recrutement");
    }

    @FXML
    private void handleLogout() {
        // Logique pour se déconnecter
        System.out.println("Déconnexion");
    }
}