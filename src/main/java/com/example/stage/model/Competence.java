package com.example.stage.model;
public class Competence {
    private String name;
    private String level; // (ex: "Débutant", "Intermédiaire", "Expert")

    public Competence() {
        // Constructeur par défaut (vide)
    }

    public Competence(String name, String level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}