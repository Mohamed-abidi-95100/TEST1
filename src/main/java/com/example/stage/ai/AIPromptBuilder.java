package com.example.stage.ai;

import com.example.stage.model.CV;
import java.time.format.DateTimeFormatter;

public class AIPromptBuilder {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String buildAnalysisPrompt(CV cv) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Analyze this CV for a professional position\n\n");

        // Personal Information
        prompt.append("Personal Information:\n");
        prompt.append("Name: ").append(cv.getName()).append("\n");
        prompt.append("Email: ").append(cv.getEmail()).append("\n");
        prompt.append("Phone: ").append(cv.getPhone()).append("\n");
        prompt.append("Address: ").append(cv.getAddress()).append("\n");
        if (cv.getLinkedin() != null && !cv.getLinkedin().isEmpty()) {
            prompt.append("LinkedIn: ").append(cv.getLinkedin()).append("\n");
        }
        if (cv.getPortfolio() != null && !cv.getPortfolio().isEmpty()) {
            prompt.append("Portfolio: ").append(cv.getPortfolio()).append("\n");
        }

        // Professional Summary
        if (cv.getSummary() != null && !cv.getSummary().isEmpty()) {
            prompt.append("\nProfessional Summary:\n");
            prompt.append(cv.getSummary()).append("\n");
        }

        // Education
        if (!cv.getEducations().isEmpty()) {
            prompt.append("\nEducation:\n");
            cv.getEducations().forEach(edu -> {
                prompt.append("- ").append(edu.getDegree())
                        .append(" from ").append(edu.getInstitution());
                if (edu.getGraduationDate() != null) {
                    prompt.append(" (")
                            .append(edu.getGraduationDate().format(DATE_FORMATTER))
                            .append(")");
                }
                prompt.append("\n");
            });
        }

        // Work Experience
        if (!cv.getWorkExperiences().isEmpty()) {
            prompt.append("\nWork Experience:\n");
            cv.getWorkExperiences().forEach(exp -> {
                prompt.append("- ").append(exp.getJobTitle())
                        .append(" at ").append(exp.getCompany());
                if (exp.getStartDate() != null) {
                    prompt.append(" (")
                            .append(exp.getStartDate().format(DATE_FORMATTER));
                    if (exp.getEndDate() != null) {
                        prompt.append(" - ")
                                .append(exp.getEndDate().format(DATE_FORMATTER));
                    }
                    prompt.append(")");
                }
                prompt.append("\n");
                if (exp.getDescription() != null && !exp.getDescription().isEmpty()) {
                    prompt.append("  ").append(exp.getDescription()).append("\n");
                }
            });
        }

        // Skills
        if (!cv.getSkills().isEmpty()) {
            prompt.append("\nSkills:\n");
            cv.getSkills().forEach(skill -> prompt.append("- ").append(skill).append("\n"));
        }

        // Projects
        if (!cv.getProjects().isEmpty()) {
            prompt.append("\nProjects:\n");
            cv.getProjects().forEach(project -> {
                prompt.append("- ").append(project.getName()).append("\n");
                if (project.getDescription() != null && !project.getDescription().isEmpty()) {
                    prompt.append("  ").append(project.getDescription()).append("\n");
                }
            });
        }

        // Languages
        if (!cv.getLanguages().isEmpty()) {
            prompt.append("\nLanguages:\n");
            cv.getLanguages().forEach(lang -> {
                prompt.append("- ").append(lang.getName())
                        .append(": ").append(lang.getProficiencyLevel().getDescription())
                        .append("\n");
            });
        }

        // Analysis Request
        prompt.append("\nPlease provide a detailed analysis of this CV, including:\n");
        prompt.append("1. Overall presentation and organization\n");
        prompt.append("2. Strength of qualifications and experience\n");
        prompt.append("3. Skills assessment and relevance\n");
        prompt.append("4. Areas for improvement\n");
        prompt.append("5. Specific recommendations for enhancement\n");
        prompt.append("6. Potential career paths based on the profile\n");

        return prompt.toString();
    }
}