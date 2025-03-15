package com.example.stage.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class WorkExperience {
    // Constants
    private static final int MIN_TITLE_LENGTH = 2;
    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MIN_COMPANY_LENGTH = 2;
    private static final int MAX_COMPANY_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;

    // Private fields
    private String jobTitle;
    private String company;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private boolean isCurrentJob;
    private String location;

    // Default constructor
    public WorkExperience() {
        this.isCurrentJob = false;
    }

    // Constructor with essential fields
    public WorkExperience(String jobTitle, String company) {
        this();
        setJobTitle(jobTitle);
        setCompany(company);
    }

    // Full constructor
    public WorkExperience(String jobTitle, String company, LocalDate startDate,
                          LocalDate endDate, String description, String location) {
        this(jobTitle, company);
        setStartDate(startDate);
        setEndDate(endDate);
        setDescription(description);
        setLocation(location);
    }

    // Getters and Setters with validation
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Job title cannot be null or empty");
        }
        if (jobTitle.length() < MIN_TITLE_LENGTH || jobTitle.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("Job title length must be between " +
                    MIN_TITLE_LENGTH + " and " + MAX_TITLE_LENGTH + " characters");
        }
        this.jobTitle = jobTitle.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        if (company == null || company.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be null or empty");
        }
        if (company.length() < MIN_COMPANY_LENGTH || company.length() > MAX_COMPANY_LENGTH) {
            throw new IllegalArgumentException("Company name length must be between " +
                    MIN_COMPANY_LENGTH + " and " + MAX_COMPANY_LENGTH + " characters");
        }
        this.company = company.trim();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate != null) {
            if (startDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Start date cannot be in the future");
            }
            if (endDate != null && startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("Start date cannot be after end date");
            }
        }
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        if (endDate != null) {
            if (endDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("End date cannot be in the future");
            }
            if (startDate != null && endDate.isBefore(startDate)) {
                throw new IllegalArgumentException("End date cannot be before start date");
            }
        }
        this.endDate = endDate;
        this.isCurrentJob = (endDate == null);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null) {
            description = description.trim();
            if (description.length() > MAX_DESCRIPTION_LENGTH) {
                throw new IllegalArgumentException("Description cannot exceed " +
                        MAX_DESCRIPTION_LENGTH + " characters");
            }
        }
        this.description = description;
    }

    public boolean isCurrentJob() {
        return isCurrentJob;
    }

    public void setCurrentJob(boolean currentJob) {
        isCurrentJob = currentJob;
        if (currentJob) {
            this.endDate = null;
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location != null ? location.trim() : null;
    }

    // Utility methods
    public String getDuration() {
        if (startDate == null) {
            return "Unknown duration";
        }

        LocalDate end = isCurrentJob ? LocalDate.now() : (endDate != null ? endDate : LocalDate.now());
        Period period = Period.between(startDate, end);

        StringBuilder duration = new StringBuilder();
        if (period.getYears() > 0) {
            duration.append(period.getYears()).append(period.getYears() == 1 ? " year" : " years");
        }
        if (period.getMonths() > 0) {
            if (duration.length() > 0) duration.append(", ");
            duration.append(period.getMonths()).append(period.getMonths() == 1 ? " month" : " months");
        }

        return duration.length() > 0 ? duration.toString() : "Less than a month";
    }

    // Override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkExperience that = (WorkExperience) o;
        return Objects.equals(jobTitle, that.jobTitle) &&
                Objects.equals(company, that.company) &&
                Objects.equals(startDate, that.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobTitle, company, startDate);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(jobTitle)
                .append(" at ")
                .append(company);

        if (location != null && !location.isEmpty()) {
            sb.append(" (").append(location).append(")");
        }

        if (startDate != null) {
            sb.append("\nDuration: ").append(getDuration());
            if (isCurrentJob) {
                sb.append(" (Current)");
            }
        }

        if (description != null && !description.isEmpty()) {
            sb.append("\n").append(description);
        }

        return sb.toString();
    }

    // Validation method
    public boolean isValid() {
        return jobTitle != null && !jobTitle.trim().isEmpty() &&
                company != null && !company.trim().isEmpty() &&
                startDate != null &&
                (endDate == null || !endDate.isBefore(startDate));
    }
}