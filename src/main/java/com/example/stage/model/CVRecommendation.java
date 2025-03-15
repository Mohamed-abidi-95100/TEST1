package com.example.stage.model;

import java.util.ArrayList;
import java.util.List;

public class CVRecommendation {
    private String profileName;
    private double matchScore;
    private List<String> matchedSkills;
    private List<String> missingSkills;
    private List<String> suggestions;
    private String detailedAnalysis;

    public CVRecommendation() {
        this.matchedSkills = new ArrayList<>();
        this.missingSkills = new ArrayList<>();
        this.suggestions = new ArrayList<>();
    }

    // Getters et Setters
    public String getProfileName() { return profileName; }
    public void setProfileName(String profileName) { this.profileName = profileName; }

    public double getMatchScore() { return matchScore; }
    public void setMatchScore(double matchScore) { this.matchScore = matchScore; }

    public List<String> getMatchedSkills() { return new ArrayList<>(matchedSkills); }
    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = new ArrayList<>(matchedSkills);
    }

    public List<String> getMissingSkills() { return new ArrayList<>(missingSkills); }
    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = new ArrayList<>(missingSkills);
    }

    public List<String> getSuggestions() { return new ArrayList<>(suggestions); }
    public void setSuggestions(List<String> suggestions) {
        this.suggestions = new ArrayList<>(suggestions);
    }

    public String getDetailedAnalysis() { return detailedAnalysis; }
    public void setDetailedAnalysis(String detailedAnalysis) {
        this.detailedAnalysis = detailedAnalysis;
    }

    // Méthodes utilitaires
    public void addMatchedSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.matchedSkills.add(skill.trim());
        }
    }

    public void addMissingSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.missingSkills.add(skill.trim());
        }
    }

    public void addSuggestion(String suggestion) {
        if (suggestion != null && !suggestion.trim().isEmpty()) {
            this.suggestions.add(suggestion.trim());
        }
    }
}