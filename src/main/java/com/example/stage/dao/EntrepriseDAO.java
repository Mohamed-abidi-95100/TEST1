package com.example.stage.dao;

import com.example.stage.model.Entreprise;
import com.example.stage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseDAO extends UtilisateurDAO {

    // Insert an enterprise into the waiting table (`entreprises_en_attente`)
    public boolean creerEntrepriseEnAttente(Entreprise entreprise) {
        String query = "INSERT INTO entreprises_en_attente (username, email, motdepasse, nom_entreprise, secteur_activite, adresse, site_web, description, logo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, entreprise.getUsername()); // Use 'username'
            statement.setString(2, entreprise.getEmail());
            statement.setString(3, entreprise.getMotdepasse());
            statement.setString(4, entreprise.getNomEntreprise());
            statement.setString(5, entreprise.getSecteurActivite());
            statement.setString(6, entreprise.getAdresse());
            statement.setString(7, entreprise.getSiteWeb());
            statement.setString(8, entreprise.getDescription());
            statement.setString(9, entreprise.getLogo()); // Set logo as null if not used
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Approve an enterprise by moving it from `entreprises_en_attente` to `utilisateurs` and `entreprises`
    public boolean approuverEntreprise(String email) {
        // Insert into `utilisateurs` table
        String insertQuery = "INSERT INTO utilisateurs (username, email, motdepasse, photo, role, est_valide) " +
                "SELECT username, email, motdepasse, NULL, 'entreprise', TRUE FROM entreprises_en_attente WHERE email = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setString(1, email);
            if (insertStatement.executeUpdate() > 0) {
                // Insert into `entreprises` table
                String moveQuery = "INSERT INTO entreprises (id_utilisateur, nom_entreprise, secteur_activite, adresse, site_web, description, logo) " +
                        "SELECT LAST_INSERT_ID(), nom_entreprise, secteur_activite, adresse, site_web, description, logo FROM entreprises_en_attente WHERE email = ?";
                try (PreparedStatement moveStatement = connection.prepareStatement(moveQuery)) {
                    moveStatement.setString(1, email);
                    if (moveStatement.executeUpdate() > 0) {
                        // Delete from `entreprises_en_attente`
                        String deleteQuery = "DELETE FROM entreprises_en_attente WHERE email = ?";
                        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                            deleteStatement.setString(1, email);
                            return deleteStatement.executeUpdate() > 0;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Reject an enterprise by deleting it from `entreprises_en_attente`
    public boolean rejeterEntreprise(String email) {
        String query = "DELETE FROM entreprises_en_attente WHERE email = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all pending enterprises from `entreprises_en_attente`
    public List<String> getEntreprisesEnAttente() {
        List<String> entreprises = new ArrayList<>();
        String query = "SELECT nom_entreprise, email FROM entreprises_en_attente";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String nomEntreprise = resultSet.getString("nom_entreprise");
                String email = resultSet.getString("email");
                entreprises.add(nomEntreprise + " - " + email); // Format: "Nom Entreprise - Email"
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entreprises;
    }

    // Retrieve an enterprise by ID from the `entreprises` table
    public Entreprise getEntrepriseById(int id) {
        Entreprise entreprise = (Entreprise) super.getUtilisateurById(id);
        if (entreprise == null || !"entreprise".equals(entreprise.getRole())) {
            return null;
        }
        String query = "SELECT nom_entreprise, secteur_activite, adresse, site_web, description, logo FROM entreprises WHERE id_utilisateur = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entreprise.setNomEntreprise(resultSet.getString("nom_entreprise"));
                entreprise.setSecteurActivite(resultSet.getString("secteur_activite"));
                entreprise.setAdresse(resultSet.getString("adresse"));
                entreprise.setSiteWeb(resultSet.getString("site_web"));
                entreprise.setDescription(resultSet.getString("description"));
                entreprise.setLogo(resultSet.getString("logo")); // Set logo as null if not used
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entreprise;
    }
}