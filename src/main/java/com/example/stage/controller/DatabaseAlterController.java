package com.example.stage.controller;

import com.example.stage.dao.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DatabaseAlterController {

    @FXML
    private Label alterStatusLabel;

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @FXML
    public void addNewColumn() {
        String alterSQL = "ALTER TABLE utilisateurs ADD COLUMN age INT NULL";
        if (utilisateurDAO.alterTable(alterSQL)) {
            alterStatusLabel.setText("Column added successfully!");
        } else {
            alterStatusLabel.setText("Failed to add column.");
        }
    }

    @FXML
    public void modifyColumn() {
        String alterSQL = "ALTER TABLE utilisateurs MODIFY COLUMN age VARCHAR(3) NULL";
        if (utilisateurDAO.alterTable(alterSQL)) {
            alterStatusLabel.setText("Column modified successfully!");
        } else {
            alterStatusLabel.setText("Failed to modify column.");
        }
    }

    @FXML
    public void dropColumn() {
        String alterSQL = "ALTER TABLE utilisateurs DROP COLUMN age";
        if (utilisateurDAO.alterTable(alterSQL)) {
            alterStatusLabel.setText("Column dropped successfully!");
        } else {
            alterStatusLabel.setText("Failed to drop column.");
        }
    }
}