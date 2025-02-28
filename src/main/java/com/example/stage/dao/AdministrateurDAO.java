package com.example.stage.dao;

import com.example.stage.model.Administrateur;
import com.example.stage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministrateurDAO extends UtilisateurDAO {
    // Create an administrator
    public boolean creerAdministrateur(Administrateur administrateur) {
        if (!super.creerUtilisateur(administrateur)) {
            return false;
        }
        String query = "INSERT INTO administrateurs (id_utilisateur, prenom, nom_complet) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, administrateur.getId());
            statement.setString(2, administrateur.getPrenom());
            statement.setString(3, administrateur.getNomComplet());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get administrator by ID
    public Administrateur getAdministrateurById(int id) {
        Administrateur administrateur = (Administrateur) super.getUtilisateurById(id);
        if (administrateur == null || !"administrateur".equals(administrateur.getRole())) {
            return null;
        }
        String query = "SELECT prenom, nom_complet FROM administrateurs WHERE id_utilisateur = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                administrateur.setPrenom(resultSet.getString("prenom"));
                administrateur.setNomComplet(resultSet.getString("nom_complet"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return administrateur;
    }
}