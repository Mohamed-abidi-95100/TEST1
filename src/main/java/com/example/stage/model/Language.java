package com.example.stage.model;

public class Language {
    private String name; // Name of the language
    private String proficiency; // Proficiency level of the language

    // Constructor
    public Language(String name, String proficiency) {
        this.name = name;
        this.proficiency = proficiency;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                ", proficiency='" + proficiency + '\'' +
                '}';
    }
}