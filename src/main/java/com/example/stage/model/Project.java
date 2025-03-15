package com.example.stage.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Project {
    // Constants for validation
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;
    private static final int MAX_URL_LENGTH = 255;



    // Project status enum
    public enum ProjectStatus {
        IN_PROGRESS("In Progress"),
        COMPLETED("Completed"),
        ON_HOLD("On Hold"),
        CANCELLED("Cancelled");

        private final String displayName;

        ProjectStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Private fields
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String projectUrl;
    private String githubUrl;
    private ProjectStatus status;
    private List<String> technologies;
    private Set<String> roles;
    private String teamSize;
    private List<String> achievements;
    private boolean isPersonalProject;

    // Constructors
    public Project(String name, String description) {
        setName(name);
        setDescription(description);
        this.technologies = new ArrayList<>();
        this.roles = new HashSet<>();
        this.achievements = new ArrayList<>();
        this.status = ProjectStatus.IN_PROGRESS;
    }

    public Project(String name, String description, LocalDate startDate, LocalDate endDate) {
        this(name, description);
        setStartDate(startDate);
        setEndDate(endDate);
    }

    // Getters and Setters with validation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Project name length must be between " +
                    MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters");
        }
        this.name = name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Project description cannot be null or empty");
        }
        if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Project description cannot exceed " +
                    MAX_DESCRIPTION_LENGTH + " characters");
        }
        this.description = description.trim();
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date cannot be null");
        }
        if (startDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the future");
        }
        if (endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
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
        updateStatus();
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        if (projectUrl != null) {
            if (projectUrl.length() > MAX_URL_LENGTH) {
                throw new IllegalArgumentException("Project URL cannot exceed " +
                        MAX_URL_LENGTH + " characters");
            }
            projectUrl = projectUrl.trim();
        }
        this.projectUrl = projectUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        if (githubUrl != null) {
            if (githubUrl.length() > MAX_URL_LENGTH) {
                throw new IllegalArgumentException("GitHub URL cannot exceed " +
                        MAX_URL_LENGTH + " characters");
            }
            if (!githubUrl.contains("github.com")) {
                throw new IllegalArgumentException("Invalid GitHub URL format");
            }
            githubUrl = githubUrl.trim();
        }
        this.githubUrl = githubUrl;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Project status cannot be null");
        }
        this.status = status;
    }

    public List<String> getTechnologies() {
        return new ArrayList<>(technologies);
    }

    public void addTechnology(String technology) {
        if (technology != null && !technology.trim().isEmpty()) {
            technologies.add(technology.trim());
        }
    }

    public void removeTechnology(String technology) {
        technologies.remove(technology);
    }

    public Set<String> getRoles() {
        return new HashSet<>(roles);
    }

    public void addRole(String role) {
        if (role != null && !role.trim().isEmpty()) {
            roles.add(role.trim());
        }
    }

    public void removeRole(String role) {
        roles.remove(role);
    }

    public String getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(String teamSize) {
        this.teamSize = teamSize != null ? teamSize.trim() : null;
    }

    public List<String> getAchievements() {
        return new ArrayList<>(achievements);
    }

    public void addAchievement(String achievement) {
        if (achievement != null && !achievement.trim().isEmpty()) {
            achievements.add(achievement.trim());
        }
    }

    public void removeAchievement(String achievement) {
        achievements.remove(achievement);
    }

    public boolean isPersonalProject() {
        return isPersonalProject;
    }

    public void setPersonalProject(boolean personalProject) {
        isPersonalProject = personalProject;
    }

    // Utility methods
    private void updateStatus() {
        if (endDate != null && endDate.isBefore(LocalDate.now())) {
            status = ProjectStatus.COMPLETED;
        } else if (startDate != null && endDate == null) {
            status = ProjectStatus.IN_PROGRESS;
        }
    }

    public String getDuration() {
        if (startDate == null) {
            return "Duration not specified";
        }
        LocalDate end = endDate != null ? endDate : LocalDate.now();
        return startDate.getMonth() + " " + startDate.getYear() + " - " +
                end.getMonth() + " " + end.getYear();
    }

    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
                description != null && !description.trim().isEmpty() &&
                startDate != null &&
                (endDate == null || !endDate.isBefore(startDate));
    }

    // Override methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name) &&
                Objects.equals(startDate, project.startDate) &&
                Objects.equals(githubUrl, project.githubUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startDate, githubUrl);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        if (startDate != null) {
            sb.append(" (").append(getDuration()).append(")");
        }
        if (!technologies.isEmpty()) {
            sb.append("\nTechnologies: ").append(String.join(", ", technologies));
        }
        if (!roles.isEmpty()) {
            sb.append("\nRoles: ").append(String.join(", ", roles));
        }
        return sb.toString();
    }

    // Clone method
    public Project clone() {
        Project clone = new Project(this.name, this.description);
        clone.startDate = this.startDate;
        clone.endDate = this.endDate;
        clone.projectUrl = this.projectUrl;
        clone.githubUrl = this.githubUrl;
        clone.status = this.status;
        clone.technologies = new ArrayList<>(this.technologies);
        clone.roles = new HashSet<>(this.roles);
        clone.teamSize = this.teamSize;
        clone.achievements = new ArrayList<>(this.achievements);
        clone.isPersonalProject = this.isPersonalProject;
        return clone;
    }
}