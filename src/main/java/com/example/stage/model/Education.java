package com.example.stage.model;

import java.time.LocalDate;

public class Education {
    private String degree;
    private String institution;
    private String graduationDate; // Peut être un String ou un objet Date

    public Education(String degree, String institution, LocalDate graduationDate) {
        this.degree = degree;
        this.institution = institution;
        this.graduationDate = graduationDate.toString();
    }

    public Education(String degree, String institution, String graduationDate) {
        this.degree = degree;
        this.institution = institution;
        this.graduationDate = graduationDate;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }
}