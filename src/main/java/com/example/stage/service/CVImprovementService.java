package com.example.stage.service;

import com.example.stage.model.CV;

import java.util.ArrayList;
import java.util.List;

public class CVImprovementService {

    public List<String> getSuggestions(CV cv) {
        List<String> suggestions = new ArrayList<>();

        if (cv.getWorkExperiences().isEmpty()) {
            suggestions.add("Ajouter plus de détails sur votre expérience professionnelle.");
        } else {
            suggestions.add("Inclure plus de projets pour montrer votre travail.");
        }

        if (cv.getSkills().isEmpty()) {
            suggestions.add("Mettre en avant vos compétences clés.");
        } else {
            suggestions.add("Développer vos compétences pour donner plus de détails.");
        }

        if (cv.getEducations().isEmpty()) {
            suggestions.add("Ajouter votre parcours éducatif.");
        } else {
            suggestions.add("Inclure des certifications ou des cours spéciaux.");
        }

        return suggestions;
    }
}