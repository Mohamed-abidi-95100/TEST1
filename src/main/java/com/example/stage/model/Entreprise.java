package com.example.stage.model;

public class Entreprise extends Utilisateur {
    private String nomEntreprise;
    private String secteurActivite;
    private String adresse;
    private String siteWeb;
    private String description;
    private String logo; // Keep this field if needed, or remove it entirely

    // Constructor
    public Entreprise(String username, String email, String motdepasse, String photo, String nomEntreprise, String secteurActivite, String adresse, String siteWeb, String description, String logo) {
        super(username, email, motdepasse, photo, "entreprise"); // Use 'username' and set 'photo' to null
        this.nomEntreprise = nomEntreprise;
        this.secteurActivite = secteurActivite;
        this.adresse = adresse;
        this.siteWeb = siteWeb;
        this.description = description;
        this.logo = logo; // Set to null if not used
    }

    // Getters and Setters
    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getSecteurActivite() {
        return secteurActivite;
    }

    public void setSecteurActivite(String secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}