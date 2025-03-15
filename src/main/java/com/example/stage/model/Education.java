package com.example.stage.model;

import java.time.LocalDate;
import java.util.Objects;

public class Education {
    // Constantes
    private static final int MIN_DEGREE_LENGTH = 2;
    private static final int MAX_DEGREE_LENGTH = 100;
    private static final int MIN_INSTITUTION_LENGTH = 2;
    private static final int MAX_INSTITUTION_LENGTH = 100;

    // Attributs privés
    private String degree;
    private String institution;
    private LocalDate graduationDate;
    private String description;
    private double gpa;
    private boolean isOngoing;
    private String location;
    private LocalDate startDate;

    // Constructeur par défaut
    public Education() {
        this.isOngoing = false;
    }

    // Constructeur avec paramètres essentiels
    public Education(String degree, String institution, LocalDate graduationDate) {
        this();
        setDegree(degree);
        setInstitution(institution);
        setGraduationDate(graduationDate);
    }

    // Constructeur complet
    public Education(String degree, String institution, LocalDate startDate,
                     LocalDate graduationDate, String description, double gpa,
                     String location) {
        this();
        setDegree(degree);
        setInstitution(institution);
        setStartDate(startDate);
        setGraduationDate(graduationDate);
        setDescription(description);
        setGpa(gpa);
        setLocation(location);
    }

    // Getters et Setters avec validation
    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        if (degree == null || degree.trim().isEmpty()) {
            throw new IllegalArgumentException("Degree cannot be null or empty");
        }
        if (degree.length() < MIN_DEGREE_LENGTH || degree.length() > MAX_DEGREE_LENGTH) {
            throw new IllegalArgumentException("Degree length must be between " +
                    MIN_DEGREE_LENGTH + " and " + MAX_DEGREE_LENGTH + " characters");
        }
        this.degree = degree.trim();
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        if (institution == null || institution.trim().isEmpty()) {
            throw new IllegalArgumentException("Institution cannot be null or empty");
        }
        if (institution.length() < MIN_INSTITUTION_LENGTH ||
                institution.length() > MAX_INSTITUTION_LENGTH) {
            throw new IllegalArgumentException("Institution length must be between " +
                    MIN_INSTITUTION_LENGTH + " and " + MAX_INSTITUTION_LENGTH + " characters");
        }
        this.institution = institution.trim();
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        if (graduationDate != null && startDate != null && graduationDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Graduation date cannot be before start date");
        }
        this.graduationDate = graduationDate;
        this.isOngoing = (graduationDate == null || graduationDate.isAfter(LocalDate.now()));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0");
        }
        this.gpa = gpa;
    }

    public boolean isOngoing() {
        return isOngoing;
    }

    public void setOngoing(boolean ongoing) {
        isOngoing = ongoing;
        if (ongoing) {
            graduationDate = null;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location != null ? location.trim() : null;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate != null && graduationDate != null && graduationDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Start date cannot be after graduation date");
        }
        this.startDate = startDate;
    }

    // Méthodes utilitaires
    public String getDuration() {
        if (startDate == null) {
            return "Unknown duration";
        }

        LocalDate endDate = isOngoing ? LocalDate.now() : graduationDate;
        if (endDate == null) {
            return "Ongoing since " + startDate.getYear();
        }

        return startDate.getYear() + " - " + endDate.getYear();
    }

    public boolean isCompleted() {
        return !isOngoing && graduationDate != null && graduationDate.isBefore(LocalDate.now());
    }

    // Override des méthodes equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return Objects.equals(degree, education.degree) &&
                Objects.equals(institution, education.institution) &&
                Objects.equals(graduationDate, education.graduationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(degree, institution, graduationDate);
    }

    // Override de toString
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(degree).append(" at ").append(institution);
        if (startDate != null) {
            sb.append(" (").append(getDuration()).append(")");
        }
        if (gpa > 0.0) {
            sb.append(" - GPA: ").append(String.format("%.2f", gpa));
        }
        if (location != null && !location.isEmpty()) {
            sb.append(" - ").append(location);
        }
        return sb.toString();
    }

    // Méthode de clonage
    @Override
    public Education clone() {
        Education clone = new Education();
        clone.degree = this.degree;
        clone.institution = this.institution;
        clone.graduationDate = this.graduationDate;
        clone.description = this.description;
        clone.gpa = this.gpa;
        clone.isOngoing = this.isOngoing;
        clone.location = this.location;
        clone.startDate = this.startDate;
        return clone;
    }

    // Méthode de validation
    public boolean isValid() {
        return degree != null && !degree.trim().isEmpty() &&
                institution != null && !institution.trim().isEmpty() &&
                (graduationDate != null || isOngoing) &&
                (startDate == null || graduationDate == null ||
                        !graduationDate.isBefore(startDate));
    }
}