package com.example.stage.model;

public class ChercheurEmploi extends Utilisateur {
    private String cv;
    private String diplomes;
    private String experiences;

    // Constructor
    public ChercheurEmploi(String username, String email, String motdepasse, String photo, String cv, String diplomes, String experiences) {
        super(username, email, motdepasse, photo, "chercheur_emploi"); // Use 'username'
        this.cv = cv;
        this.diplomes = diplomes;
        this.experiences = experiences;
    }



        // Constructor with minimal required fields
        public ChercheurEmploi(String username, String email, String motdepasse, String photo, String role) {
            super(username, email, motdepasse, photo, role);
            this.cv = null;
            this.diplomes = null;
            this.experiences = null;
        }

    // Getters and Setters
    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getDiplomes() {
        return diplomes;
    }

    public void setDiplomes(String diplomes) {
        this.diplomes = diplomes;
    }

    public String getExperiences() {
        return experiences;
    }

    public void setExperiences(String experiences) {
        this.experiences = experiences;
    }
}