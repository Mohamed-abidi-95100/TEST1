package com.example.stage.model;

public class Utilisateur {
    private int id;
    private String username; // Use 'username' instead of 'nom'
    private String email;
    private String motdepasse;
    private String photo;
    private String role;

    // Constructor
    public Utilisateur() {}

    public Utilisateur(String username, String email, String motdepasse, String photo, String role) {
        this.username = username; // Use 'username'
        this.email = email;
        this.motdepasse = motdepasse;
        this.photo = photo;
        this.role = role;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() { // Use 'getUsername' instead of 'getNom'
        return username;
    }

    public void setUsername(String username) { // Use 'setUsername' instead of 'setNom'
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}