package com.example.stage.model;

import java.time.LocalDate;

public class Certificate {
    private String name; // Name of the certificate
    private String institution; // Institution that issued the certificate
    private LocalDate date; // Date of receiving the certificate

    // Constructor
    public Certificate(String name, String institution, LocalDate date) {
        this.name = name;
        this.institution = institution;
        this.date = date;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "name='" + name + '\'' +
                ", institution='" + institution + '\'' +
                ", date=" + date +
                '}';
    }
}