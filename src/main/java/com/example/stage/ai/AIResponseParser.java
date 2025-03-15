package com.example.stage.ai;

import com.example.stage.model.CVAnalysis;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AIResponseParser {
    // Constantes pour les patterns de reconnaissance
    private static final String OVERALL_PATTERN = "(?i)Overall.*?:(.+?)(?=\\n|$)";
    private static final String PRESENTATION_PATTERN = "(?i)Presentation.*?:(.+?)(?=\\n\\n|$)";
    private static final String SKILLS_PATTERN = "(?i)Skills.*?:(.+?)(?=\\n\\n|$)";
    private static final String EXPERIENCE_PATTERN = "(?i)Experience.*?:(.+?)(?=\\n\\n|$)";
    private static final String STRENGTHS_PATTERN = "(?i)Strengths?:([\\s\\S]*?)(?=\\n\\n|Weaknesses|Areas for Improvement|$)";
    private static final String WEAKNESSES_PATTERN = "(?i)(Weaknesses|Areas for Improvement):([\\s\\S]*?)(?=\\n\\n|Recommendations|$)";
    private static final String RECOMMENDATIONS_PATTERN = "(?i)Recommendations:([\\s\\S]*?)(?=\\n\\n|Career Paths|$)";
    private static final String CAREER_PATHS_PATTERN = "(?i)Career Paths:([\\s\\S]*?)(?=\\n\\n|$)";

    public CVAnalysis parseAnalysisResponse(String aiResponse) {
        if (aiResponse == null || aiResponse.trim().isEmpty()) {
            throw new IllegalArgumentException("AI response cannot be null or empty");
        }

        CVAnalysis analysis = new CVAnalysis();
        analysis.setRawAnalysis(aiResponse);

        // Extraire les différentes parties de l'analyse
        extractOverallScore(aiResponse, analysis);
        extractPresentationAnalysis(aiResponse, analysis);
        extractSkillsAnalysis(aiResponse, analysis);
        extractExperienceAnalysis(aiResponse, analysis);
        extractListItems(aiResponse, STRENGTHS_PATTERN, analysis::addStrength);
        extractListItems(aiResponse, WEAKNESSES_PATTERN, analysis::addWeakness);
        extractListItems(aiResponse, RECOMMENDATIONS_PATTERN, analysis::addRecommendation);
        extractListItems(aiResponse, CAREER_PATHS_PATTERN, analysis::addCareerPath);

        return analysis;
    }

    private void extractOverallScore(String response, CVAnalysis analysis) {
        String score = extractPattern(response, OVERALL_PATTERN);
        if (score != null) {
            analysis.setOverallScore(score.trim());
        }
    }

    private void extractPresentationAnalysis(String response, CVAnalysis analysis) {
        String presentation = extractPattern(response, PRESENTATION_PATTERN);
        if (presentation != null) {
            analysis.setPresentationAnalysis(presentation.trim());
        }
    }

    private void extractSkillsAnalysis(String response, CVAnalysis analysis) {
        String skills = extractPattern(response, SKILLS_PATTERN);
        if (skills != null) {
            analysis.setSkillsAnalysis(skills.trim());
        }
    }

    private void extractExperienceAnalysis(String response, CVAnalysis analysis) {
        String experience = extractPattern(response, EXPERIENCE_PATTERN);
        if (experience != null) {
            analysis.setExperienceAnalysis(experience.trim());
        }
    }

    private void extractListItems(String response, String pattern, java.util.function.Consumer<String> consumer) {
        String content = extractPattern(response, pattern);
        if (content != null) {
            Arrays.stream(content.split("(?m)^[-•*]\\s*"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(consumer);
        }
    }

    private String extractPattern(String text, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        if (m.find()) {
            return m.groupCount() > 1 ? m.group(2) : m.group(1);
        }
        return null;
    }
}