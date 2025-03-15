package com.example.stage.model;

import java.time.LocalDate;
import java.util.Objects;

public class Certificate {
    // Constants for validation
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 200;
    private static final int MIN_INSTITUTION_LENGTH = 2;
    private static final int MAX_INSTITUTION_LENGTH = 100;

    // Private fields
    private String name;
    private String institution;
    private LocalDate dateReceived;
    private LocalDate expirationDate;
    private String credentialId;
    private String credentialUrl;
    private String description;
    private boolean isExpirable;

    // Constructors
    public Certificate(String name, String institution, LocalDate dateReceived) {
        setName(name);
        setInstitution(institution);
        setDateReceived(dateReceived);
    }

    public Certificate(String name, String institution, LocalDate dateReceived,
                       LocalDate expirationDate, String credentialId, String credentialUrl) {
        this(name, institution, dateReceived);
        setExpirationDate(expirationDate);
        setCredentialId(credentialId);
        setCredentialUrl(credentialUrl);
    }

    // Getters and Setters with validation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Certificate name cannot be null or empty");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Certificate name length must be between " +
                    MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters");
        }
        this.name = name.trim();
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        if (institution == null || institution.trim().isEmpty()) {
            throw new IllegalArgumentException("Institution name cannot be null or empty");
        }
        if (institution.length() < MIN_INSTITUTION_LENGTH ||
                institution.length() > MAX_INSTITUTION_LENGTH) {
            throw new IllegalArgumentException("Institution name length must be between " +
                    MIN_INSTITUTION_LENGTH + " and " + MAX_INSTITUTION_LENGTH + " characters");
        }
        this.institution = institution.trim();
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        if (dateReceived == null) {
            throw new IllegalArgumentException("Date received cannot be null");
        }
        if (dateReceived.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date received cannot be in the future");
        }
        if (expirationDate != null && dateReceived.isAfter(expirationDate)) {
            throw new IllegalArgumentException("Date received cannot be after expiration date");
        }
        this.dateReceived = dateReceived;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        if (expirationDate != null) {
            if (dateReceived != null && expirationDate.isBefore(dateReceived)) {
                throw new IllegalArgumentException("Expiration date cannot be before date received");
            }
        }
        this.expirationDate = expirationDate;
        this.isExpirable = (expirationDate != null);
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId != null ? credentialId.trim() : null;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl != null ? credentialUrl.trim() : null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description != null ? description.trim() : null;
    }

    public boolean isExpirable() {
        return isExpirable;
    }

    // Utility methods
    public boolean isExpired() {
        if (!isExpirable || expirationDate == null) {
            return false;
        }
        return expirationDate.isBefore(LocalDate.now());
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
                institution != null && !institution.trim().isEmpty() &&
                dateReceived != null &&
                (expirationDate == null || !expirationDate.isBefore(dateReceived));
    }

    public String getStatus() {
        if (!isExpirable) {
            return "No Expiration";
        }
        return isExpired() ? "Expired" : "Valid";
    }

    // Override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(institution, that.institution) &&
                Objects.equals(dateReceived, that.dateReceived) &&
                Objects.equals(credentialId, that.credentialId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, institution, dateReceived, credentialId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" from ").append(institution);
        sb.append(" (Received: ").append(dateReceived);
        if (isExpirable) {
            sb.append(", ").append(getStatus());
        }
        if (credentialId != null) {
            sb.append(", ID: ").append(credentialId);
        }
        sb.append(")");
        return sb.toString();
    }

    // Clone method
    public Certificate clone() {
        Certificate clone = new Certificate(
                this.name,
                this.institution,
                this.dateReceived
        );
        clone.expirationDate = this.expirationDate;
        clone.credentialId = this.credentialId;
        clone.credentialUrl = this.credentialUrl;
        clone.description = this.description;
        clone.isExpirable = this.isExpirable;
        return clone;
    }

    // Utility methods for date formatting
    public String getDateReceivedString() {
        return dateReceived != null ? dateReceived.toString() : "";
    }

    public String getExpirationDateString() {
        return expirationDate != null ? expirationDate.toString() : "";
    }
}