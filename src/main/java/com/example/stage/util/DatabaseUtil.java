package com.example.stage.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Esprimcareer1";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    // Obtenir une connexion à la base de données
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            Connection connection = getConnection();
            if (connection != null) {
                System.out.println("Connexion réussie !");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}