package com.example.stage.model;

import java.time.LocalDateTime;
import java.util.*;

public class CVAnalysis {
    // Scores et métriques
    private double completenessScore;
    private double presentationScore;
    private double experienceScore;
    private double educationScore;
    private double skillsRelevanceScore;
    private Map<String, Double> skillsMatchScore;

    // Analyses textuelles
    private List<String> suggestions;
    private List<String> strengths;
    private List<String> improvements;
    private String overallFeedback;

    // Analyses spécifiques
    private Map<String, String> sectionFeedback;
    private List<String> missingElements;
    private List<String> recommendedSkills;

    // Métadonnées
    private LocalDateTime analysisDate;
    private String analysisVersion;
    private String jobMarketTarget;

    // Constructeur
    public CVAnalysis() {
        this.skillsMatchScore = new HashMap<>();
        this.suggestions = new ArrayList<>();
        this.strengths = new ArrayList<>();
        this.improvements = new ArrayList<>();
        this.sectionFeedback = new HashMap<>();
        this.missingElements = new ArrayList<>();
        this.recommendedSkills = new ArrayList<>();
        this.analysisDate = LocalDateTime.now();
        this.analysisVersion = "1.0";
    }

    // Getters et Setters avec validation
    public double getCompletenessScore() {
        return completenessScore;
    }

    public void setCompletenessScore(double completenessScore) {
        if (completenessScore < 0 || completenessScore > 100) {
            throw new IllegalArgumentException("Completeness score must be between 0 and 100");
        }
        this.completenessScore = completenessScore;
    }

    public double getPresentationScore() {
        return presentationScore;
    }

    public void setPresentationScore(double presentationScore) {
        if (presentationScore < 0 || presentationScore > 100) {
            throw new IllegalArgumentException("Presentation score must be between 0 and 100");
        }
        this.presentationScore = presentationScore;
    }

    public double getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(double experienceScore) {
        if (experienceScore < 0 || experienceScore > 100) {
            throw new IllegalArgumentException("Experience score must be between 0 and 100");
        }
        this.experienceScore = experienceScore;
    }

    public double getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(double educationScore) {
        if (educationScore < 0 || educationScore > 100) {
            throw new IllegalArgumentException("Education score must be between 0 and 100");
        }
        this.educationScore = educationScore;
    }

    public double getSkillsRelevanceScore() {
        return skillsRelevanceScore;
    }

    public void setSkillsRelevanceScore(double skillsRelevanceScore) {
        if (skillsRelevanceScore < 0 || skillsRelevanceScore > 100) {
            throw new IllegalArgumentException("Skills relevance score must be between 0 and 100");
        }
        this.skillsRelevanceScore = skillsRelevanceScore;
    }

    public Map<String, Double> getSkillsMatchScore() {
        return new HashMap<>(skillsMatchScore);
    }

    public void setSkillsMatchScore(Map<String, Double> skillsMatchScore) {
        this.skillsMatchScore = new HashMap<>(skillsMatchScore);
    }

    public void addSkillScore(String skill, double score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Skill score must be between 0 and 100");
        }
        this.skillsMatchScore.put(skill, score);
    }

    public List<String> getSuggestions() {
        return new ArrayList<>(suggestions);
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = new ArrayList<>(suggestions);
    }

    public void addSuggestion(String suggestion) {
        if (suggestion != null && !suggestion.trim().isEmpty()) {
            this.suggestions.add(suggestion.trim());
        }
    }

    public List<String> getStrengths() {
        return new ArrayList<>(strengths);
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = new ArrayList<>(strengths);
    }

    public void addStrength(String strength) {
        if (strength != null && !strength.trim().isEmpty()) {
            this.strengths.add(strength.trim());
        }
    }

    public List<String> getImprovements() {
        return new ArrayList<>(improvements);
    }

    public void setImprovements(List<String> improvements) {
        this.improvements = new ArrayList<>(improvements);
    }

    public void addImprovement(String improvement) {
        if (improvement != null && !improvement.trim().isEmpty()) {
            this.improvements.add(improvement.trim());
        }
    }

    public String getOverallFeedback() {
        return overallFeedback;
    }

    public void setOverallFeedback(String overallFeedback) {
        this.overallFeedback = overallFeedback;
    }

    public Map<String, String> getSectionFeedback() {
        return new HashMap<>(sectionFeedback);
    }

    public void setSectionFeedback(Map<String, String> sectionFeedback) {
        this.sectionFeedback = new HashMap<>(sectionFeedback);
    }

    public void addSectionFeedback(String section, String feedback) {
        if (section != null && feedback != null) {
            this.sectionFeedback.put(section.trim(), feedback.trim());
        }
    }

    public List<String> getMissingElements() {
        return new ArrayList<>(missingElements);
    }

    public void setMissingElements(List<String> missingElements) {
        this.missingElements = new ArrayList<>(missingElements);
    }

    public void addMissingElement(String element) {
        if (element != null && !element.trim().isEmpty()) {
            this.missingElements.add(element.trim());
        }
    }

    public List<String> getRecommendedSkills() {
        return new ArrayList<>(recommendedSkills);
    }

    public void setRecommendedSkills(List<String> recommendedSkills) {
        this.recommendedSkills = new ArrayList<>(recommendedSkills);
    }

    public void addRecommendedSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.recommendedSkills.add(skill.trim());
        }
    }

    public LocalDateTime getAnalysisDate() {
        return analysisDate;
    }

    public String getAnalysisVersion() {
        return analysisVersion;
    }

    public void setAnalysisVersion(String analysisVersion) {
        this.analysisVersion = analysisVersion;
    }

    public String getJobMarketTarget() {
        return jobMarketTarget;
    }

    public void setJobMarketTarget(String jobMarketTarget) {
        this.jobMarketTarget = jobMarketTarget;
    }

    // Méthodes utilitaires
    public double getOverallScore() {
        return (completenessScore + presentationScore + experienceScore +
                educationScore + skillsRelevanceScore) / 5.0;
    }

    public boolean isComplete() {
        return completenessScore >= 80.0;
    }

    public List<String> getPriorityImprovements() {
        List<String> priorities = new ArrayList<>();
        if (completenessScore < 70) priorities.add("Complete missing information");
        if (presentationScore < 70) priorities.add("Improve CV presentation");
        if (skillsRelevanceScore < 70) priorities.add("Update skills section");
        return priorities;
    }

    // Override toString pour l'affichage
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CV Analysis Report (").append(analysisDate).append(")\n");
        sb.append("Version: ").append(analysisVersion).append("\n\n");

        sb.append("Overall Score: ").append(String.format("%.2f%%", getOverallScore())).append("\n");
        sb.append("Completeness: ").append(String.format("%.2f%%", completenessScore)).append("\n");
        sb.append("Presentation: ").append(String.format("%.2f%%", presentationScore)).append("\n");
        sb.append("Experience: ").append(String.format("%.2f%%", experienceScore)).append("\n");
        sb.append("Education: ").append(String.format("%.2f%%", educationScore)).append("\n");
        sb.append("Skills Relevance: ").append(String.format("%.2f%%", skillsRelevanceScore)).append("\n\n");

        if (!strengths.isEmpty()) {
            sb.append("Strengths:\n");
            strengths.forEach(s -> sb.append("+ ").append(s).append("\n"));
            sb.append("\n");
        }

        if (!improvements.isEmpty()) {
            sb.append("Areas for Improvement:\n");
            improvements.forEach(i -> sb.append("- ").append(i).append("\n"));
            sb.append("\n");
        }

        if (!suggestions.isEmpty()) {
            sb.append("Suggestions:\n");
            suggestions.forEach(s -> sb.append("* ").append(s).append("\n"));
        }

        return sb.toString();
    }

    // Override equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CVAnalysis that = (CVAnalysis) o;
        return Objects.equals(analysisDate, that.analysisDate) &&
                Objects.equals(analysisVersion, that.analysisVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(analysisDate, analysisVersion);
    }

    public void addWeakness(String s) {
    }

    public void setOverallScore(String trim) {
    }

    public void setPresentationAnalysis(String trim) {
    }

    public void setExperienceAnalysis(String trim) {
    }

    public void setSkillsAnalysis(String trim) {
    }

    public void setRawAnalysis(String aiResponse) {
    }

    public void addRecommendation(String s) {
    }

    public void addCareerPath(String s) {
    }
}