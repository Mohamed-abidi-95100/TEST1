package com.example.stage.controller;

import com.example.stage.dao.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class AdminController {

    @FXML
    private ListView<String> entreprisesEnAttenteListView; // Liste des entreprises en attente
    @FXML
    private Button approuverButton;
    @FXML
    private Button rejeterButton;
    @FXML
    private Label messageLabel;

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    // Méthode appelée lors du chargement de la page
    @FXML
    public void initialize() {
        chargerEntreprisesEnAttente();
    }

    // Charger la liste des entreprises en attente
    private void chargerEntreprisesEnAttente() {
        List<String> entreprises = utilisateurDAO.getEntreprisesEnAttente();
        entreprisesEnAttenteListView.getItems().addAll(entreprises);
    }

    // Approuver une entreprise
    @FXML
    public void approuverEntreprise() {
        String selectedItem = entreprisesEnAttenteListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            messageLabel.setText("Veuillez sélectionner une entreprise à approuver.");
            return;
        }

        String email = extraireEmailDeTexte(selectedItem);

        if (utilisateurDAO.approuverEntreprise(email)) {
            entreprisesEnAttenteListView.getItems().remove(selectedItem); // Retirer l'entreprise de la liste
            messageLabel.setText("Entreprise approuvée avec succès !");
        } else {
            messageLabel.setText("Échec de l'approbation de l'entreprise.");
        }
    }

    // Rejeter une entreprise
    @FXML
    public void rejeterEntreprise() {
        String selectedItem = entreprisesEnAttenteListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            messageLabel.setText("Veuillez sélectionner une entreprise à rejeter.");
            return;
        }

        String email = extraireEmailDeTexte(selectedItem);

        if (utilisateurDAO.rejeterEntreprise(email)) {
            entreprisesEnAttenteListView.getItems().remove(selectedItem); // Retirer l'entreprise de la liste
            messageLabel.setText("Entreprise rejetée avec succès !");
        } else {
            messageLabel.setText("Échec du rejet de l'entreprise.");
        }
    }

    // Extraire l'email depuis le texte affiché dans la liste
    private String extraireEmailDeTexte(String text) {
        // Supposons que le format soit "Nom Entreprise - Email"
        String[] parts = text.split(" - ");
        return parts[1].trim(); // Récupérer la partie après " - "
    }
}