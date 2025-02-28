package com.example.stage.service;

import com.example.stage.dao.UtilisateurDAO;
import com.example.stage.model.Utilisateur;
import com.example.stage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService {
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    // Create a user
    public boolean creerUtilisateur(Utilisateur utilisateur) {
        return utilisateurDAO.creerUtilisateur(utilisateur);
    }

    // Get user by ID
    public Utilisateur getUtilisateurById(int id) {
        return utilisateurDAO.getUtilisateurById(id);
    }

    // Get user by email
    public Utilisateur getUtilisateurByEmail(String email) {
        return utilisateurDAO.getUtilisateurByEmail(email);
    }

    // Get all users
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.getAllUtilisateurs();
    }

    // Mettre à jour un utilisateur
    public boolean mettreAJourUtilisateur(Utilisateur utilisateur) {
        return utilisateurDAO.mettreAJourUtilisateur(utilisateur);
    }

    // Supprimer un utilisateur
    public boolean supprimerUtilisateur(int id) {
        return utilisateurDAO.supprimerUtilisateur(id);
    }


    // Récupérer la liste des entreprises en attente
    public List<String> getEntreprisesEnAttente() {
        return utilisateurDAO.getEntreprisesEnAttente();
    }

    // Approve an enterprise
    public boolean approuverEntreprise(String email) {
        return utilisateurDAO.approuverEntreprise(email);
    }

    // Rejeter une entreprise
    public boolean rejeterEntreprise(String email) {
        return utilisateurDAO.rejeterEntreprise(email);
    }
    public boolean existsAdmin() {
        String query = "SELECT COUNT(*) AS count FROM utilisateurs WHERE role = 'administrateur'";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("count") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public List<String> getAllEntreprises() {
        return utilisateurDAO.getAllEntreprises();
    }
}