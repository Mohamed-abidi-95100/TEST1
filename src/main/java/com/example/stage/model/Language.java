package com.example.stage.model;

import java.util.Objects;

public class Language {
    // Constants for validation
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 50;

    // Language proficiency levels following CEFR standards
    public enum ProficiencyLevel {
        A1("Beginner"),
        A2("Elementary"),
        B1("Intermediate"),
        B2("Upper Intermediate"),
        C1("Advanced"),
        C2("Mastery");

        private final String description;

        ProficiencyLevel(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    // Private fields
    private String name;
    private ProficiencyLevel proficiencyLevel;
    private boolean isNative;
    private String certificateDetails;
    private boolean hasOralProficiency;
    private boolean hasWrittenProficiency;

    // Constructors
    public Language(String name, ProficiencyLevel proficiencyLevel) {
        setName(name);
        setProficiencyLevel(proficiencyLevel);
    }

    public Language(String name, ProficiencyLevel proficiencyLevel, boolean isNative) {
        this(name, proficiencyLevel);
        this.isNative = isNative;
    }

    // Getters and Setters with validation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Language name cannot be null or empty");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Language name length must be between " +
                    MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters");
        }
        this.name = name.trim();
    }

    public ProficiencyLevel getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(ProficiencyLevel proficiencyLevel) {
        if (proficiencyLevel == null && !isNative) {
            throw new IllegalArgumentException("Proficiency level cannot be null for non-native languages");
        }
        this.proficiencyLevel = proficiencyLevel;
    }

    public boolean isNative() {
        return isNative;
    }

    public void setNative(boolean isNative) {
        this.isNative = isNative;
        if (isNative) {
            this.proficiencyLevel = ProficiencyLevel.C2;
        }
    }

    public String getCertificateDetails() {
        return certificateDetails;
    }

    public void setCertificateDetails(String certificateDetails) {
        this.certificateDetails = certificateDetails != null ? certificateDetails.trim() : null;
    }

    public boolean hasOralProficiency() {
        return hasOralProficiency;
    }

    public void setHasOralProficiency(boolean hasOralProficiency) {
        this.hasOralProficiency = hasOralProficiency;
    }

    public boolean hasWrittenProficiency() {
        return hasWrittenProficiency;
    }

    public void setHasWrittenProficiency(boolean hasWrittenProficiency) {
        this.hasWrittenProficiency = hasWrittenProficiency;
    }

    // Utility methods
    public String getProficiencyDescription() {
        if (isNative) {
            return "Native";
        }
        return proficiencyLevel != null ?
                proficiencyLevel.name() + " - " + proficiencyLevel.getDescription() :
                "Not specified";
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
                (isNative || proficiencyLevel != null);
    }

    // Override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return Objects.equals(name.toLowerCase(), language.name.toLowerCase()) &&
                Objects.equals(proficiencyLevel, language.proficiencyLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase(), proficiencyLevel);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (isNative) {
            sb.append(" (Native)");
        } else {
            sb.append(" (").append(getProficiencyDescription()).append(")");
        }
        if (certificateDetails != null && !certificateDetails.isEmpty()) {
            sb.append(" - ").append(certificateDetails);
        }
        return sb.toString();
    }

    // Clone method
    public Language clone() {
        Language clone = new Language(this.name, this.proficiencyLevel);
        clone.isNative = this.isNative;
        clone.certificateDetails = this.certificateDetails;
        clone.hasOralProficiency = this.hasOralProficiency;
        clone.hasWrittenProficiency = this.hasWrittenProficiency;
        return clone;
    }

    // Comparison method for sorting
    public int compareTo(Language other) {
        if (this.isNative != other.isNative) {
            return this.isNative ? -1 : 1;
        }
        if (this.proficiencyLevel != other.proficiencyLevel &&
                this.proficiencyLevel != null && other.proficiencyLevel != null) {
            return other.proficiencyLevel.compareTo(this.proficiencyLevel);
        }
        return this.name.compareToIgnoreCase(other.name);
    }
}