package com.example.stage.model;

public class ProfileConfig {
    private String name;
    private String description;
    private String[] requiredSkills;
    private String[] optionalSkills;
    private int minimumExperience;
    private String[] educationLevels;

    // Constructeur par défaut
    public ProfileConfig() {}

    // Getters et Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String[] getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(String[] requiredSkills) { this.requiredSkills = requiredSkills; }

    public String[] getOptionalSkills() { return optionalSkills; }
    public void setOptionalSkills(String[] optionalSkills) { this.optionalSkills = optionalSkills; }

    public int getMinimumExperience() { return minimumExperience; }
    public void setMinimumExperience(int minimumExperience) { this.minimumExperience = minimumExperience; }

    public String[] getEducationLevels() { return educationLevels; }
    public void setEducationLevels(String[] educationLevels) { this.educationLevels = educationLevels; }
}