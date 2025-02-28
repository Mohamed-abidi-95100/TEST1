package com.example.stage.controller;

import com.example.stage.service.UtilisateurService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.List;

public class AdminDemandesController {
    @FXML
    private ListView<String> entreprisesEnAttenteListView;
    @FXML
    private Button approuverButton;
    @FXML
    private Button rejeterButton;
    @FXML
    private Label messageLabel;

    private UtilisateurService utilisateurService = new UtilisateurService();

    // Load pending enterprise requests
    @FXML
    public void initialize() {
        chargerDemandesEntreprises();
    }

    private void chargerDemandesEntreprises() {
        List<String> entreprises = utilisateurService.getEntreprisesEnAttente();
        entreprisesEnAttenteListView.getItems().clear();
        entreprisesEnAttenteListView.getItems().addAll(entreprises);
    }

    // Approve an enterprise
    @FXML
    public void approuverEntreprise() {
        String selectedItem = entreprisesEnAttenteListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            messageLabel.setText("Veuillez sélectionner une entreprise à approuver.");
            return;
        }

        String email = extraireEmailDeTexte(selectedItem);
        if (utilisateurService.approuverEntreprise(email)) {
            entreprisesEnAttenteListView.getItems().remove(selectedItem);
            messageLabel.setText("Entreprise approuvée avec succès !");
        } else {
            messageLabel.setText("Échec de l'approbation de l'entreprise.");
        }
        chargerDemandesEntreprises();
    }

    // Reject an enterprise
    @FXML
    public void rejeterEntreprise() {
        String selectedItem = entreprisesEnAttenteListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            messageLabel.setText("Veuillez sélectionner une entreprise à rejeter.");
            return;
        }

        String email = extraireEmailDeTexte(selectedItem);
        if (utilisateurService.rejeterEntreprise(email)) {
            entreprisesEnAttenteListView.getItems().remove(selectedItem);
            messageLabel.setText("Entreprise rejetée avec succès !");
        } else {
            messageLabel.setText("Échec du rejet de l'entreprise.");
        }
        chargerDemandesEntreprises();
    }

    // Extract email from the displayed text
    private String extraireEmailDeTexte(String text) {
        String[] parts = text.split(" - ");
        return parts[1].trim(); // Assuming format: "Nom Entreprise - Email"
    }
}