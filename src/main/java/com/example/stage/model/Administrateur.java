package com.example.stage.model;

public class Administrateur extends Utilisateur {
    private String prenom;
    private String nomComplet;

    // Constructor
    public Administrateur() {}

    public Administrateur(String username, String email, String motdepasse, String photo, String prenom, String nomComplet) {
        super(username, email, motdepasse, photo, "administrateur");
        this.prenom = prenom;
        this.nomComplet = nomComplet;
    }

    // Getters and Setters
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
}