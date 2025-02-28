package com.example.stage.service;

import com.example.stage.dao.EntrepriseDAO;
import com.example.stage.model.Entreprise;

public class EntrepriseService {
    private EntrepriseDAO entrepriseDAO = new EntrepriseDAO();

    // Insérer une entreprise en attente
    public boolean creerEntrepriseEnAttente(Entreprise entreprise) {
        return entrepriseDAO.creerEntrepriseEnAttente(entreprise);
    }

    // Autres méthodes si nécessaire...
}