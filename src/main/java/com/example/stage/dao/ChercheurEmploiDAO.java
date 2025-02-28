package com.example.stage.dao;

import com.example.stage.model.ChercheurEmploi;
import com.example.stage.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChercheurEmploiDAO extends UtilisateurDAO {

    // Insérer un chercheur d'emploi (seulement dans utilisateurs)
    public boolean creerChercheurEmploi(ChercheurEmploi chercheurEmploi) {
        return super.creerUtilisateur(chercheurEmploi); // Laissez la trigger gérer le reste
    }
}