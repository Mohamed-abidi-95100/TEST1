package com.example.stage.dao;

import com.example.stage.model.Utilisateur;
import com.example.stage.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    // Mettre à jour un utilisateur
    public boolean mettreAJourUtilisateur(Utilisateur utilisateur) {
        String query = "UPDATE utilisateurs SET nom = ?, email = ?, motdepasse = ?, photo = ?, role = ? WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utilisateur.getUsername());
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotdepasse());
            statement.setString(4, utilisateur.getPhoto());
            statement.setString(5, utilisateur.getRole());
            statement.setInt(6, utilisateur.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Other existing methods...

    // Insérer un utilisateur
    public boolean creerUtilisateur(Utilisateur utilisateur) {
        String query = "INSERT INTO utilisateurs (username, email, motdepasse, photo, role) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utilisateur.getUsername()); // Use 'username'
            statement.setString(2, utilisateur.getEmail());
            statement.setString(3, utilisateur.getMotdepasse());
            statement.setString(4, utilisateur.getPhoto());
            statement.setString(5, utilisateur.getRole());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer un utilisateur par ID
    public Utilisateur getUtilisateurById(int id) {
        String query = "SELECT * FROM utilisateurs WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToUtilisateur(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Récupérer un utilisateur par email
    public Utilisateur getUtilisateurByEmail(String email) {
        String query = "SELECT * FROM utilisateurs WHERE email = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToUtilisateur(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Récupérer tous les utilisateurs
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM utilisateurs";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                utilisateurs.add(mapToUtilisateur(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    // Mapper un ResultSet vers un objet Utilisateur
    private Utilisateur mapToUtilisateur(ResultSet resultSet) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(resultSet.getInt("id"));
        utilisateur.setUsername(resultSet.getString("username")); // Use 'username'
        utilisateur.setEmail(resultSet.getString("email"));
        utilisateur.setMotdepasse(resultSet.getString("motdepasse"));
        utilisateur.setPhoto(resultSet.getString("photo"));
        utilisateur.setRole(resultSet.getString("role"));
        return utilisateur;
    }

    // Supprimer un utilisateur
    public boolean supprimerUtilisateur(int id) {
        String query = "DELETE FROM utilisateurs WHERE id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Récupérer la liste des entreprises en attente
    public List<String> getEntreprisesEnAttente() {
        List<String> entreprises = new ArrayList<>();
        String query = "SELECT nom_entreprise, email FROM entreprises_en_attente";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String nomEntreprise = resultSet.getString("nom_entreprise");
                String email = resultSet.getString("email");
                entreprises.add(nomEntreprise + " - " + email); // Format : "Nom Entreprise - Email"
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entreprises;
    }

    // Approve an enterprise
    public boolean approuverEntreprise(String email) {
        try (Connection connection = DatabaseUtil.getConnection()) {
            connection.setAutoCommit(false); // Début de transaction

            // Étape 1 : Insérer dans `utilisateurs` et récupérer l'ID généré
            String insertUtilisateurQuery = "INSERT INTO utilisateurs (username, email, motdepasse, role, est_valide) " +
                    "SELECT username, email, motdepasse, 'entreprise', TRUE FROM entreprises_en_attente WHERE email = ?";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertUtilisateurQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertStatement.setString(1, email);
                if (insertStatement.executeUpdate() == 0) {
                    connection.rollback();
                    return false;
                }
                // Récupérer l'ID généré
                ResultSet generatedKeys = insertStatement.getGeneratedKeys();
                if (!generatedKeys.next()) {
                    connection.rollback();
                    return false;
                }
                int utilisateurId = generatedKeys.getInt(1); // Récupération de l'ID
                generatedKeys.close();

                // Étape 2 : Insérer dans `entreprises` en utilisant l'ID récupéré
                String insertEntrepriseQuery = "INSERT INTO entreprises (id_utilisateur, nom_entreprise, secteur_activite, adresse, site_web, description, logo) " +
                        "SELECT ?, nom_entreprise, secteur_activite, adresse, site_web, description, logo FROM entreprises_en_attente WHERE email = ?";
                try (PreparedStatement insertEntrepriseStatement = connection.prepareStatement(insertEntrepriseQuery)) {
                    insertEntrepriseStatement.setInt(1, utilisateurId);
                    insertEntrepriseStatement.setString(2, email);
                    if (insertEntrepriseStatement.executeUpdate() == 0) {
                        connection.rollback();
                        return false;
                    }
                }

                // Étape 3 : Supprimer de `entreprises_en_attente`
                String deleteQuery = "DELETE FROM entreprises_en_attente WHERE email = ?";
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                    deleteStatement.setString(1, email);
                    if (deleteStatement.executeUpdate() == 0) {
                        connection.rollback();
                        return false;
                    }
                }
            }

            connection.commit(); // Tout est OK, on valide la transaction
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // Rejeter une entreprise
    public boolean rejeterEntreprise(String email) {
        String query = "DELETE FROM entreprises_en_attente WHERE email = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllEntreprises() {
        String query = "SELECT nom_entreprise, email FROM entreprises";
        List<String> entreprises = new ArrayList<>();
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

    public boolean alterTable(String alterSQL) {
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(alterSQL)) {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
