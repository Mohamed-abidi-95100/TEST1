package com.example.stage.service;

import com.example.stage.dao.AdministrateurDAO;
import com.example.stage.model.Administrateur;

public class AdministrateurService {
    private AdministrateurDAO administrateurDAO = new AdministrateurDAO();

    // Create an administrator
    public boolean creerAdministrateur(Administrateur administrateur) {
        return administrateurDAO.creerAdministrateur(administrateur);
    }

    // Get administrator by ID
    public Administrateur getAdministrateurById(int id) {
        return administrateurDAO.getAdministrateurById(id);
    }
}