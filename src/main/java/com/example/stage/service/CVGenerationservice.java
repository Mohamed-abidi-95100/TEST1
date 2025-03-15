package com.example.stage.service;

import com.example.stage.model.CV;
import com.example.stage.model.CVData;

import java.io.IOException;

public class CVGenerationservice {
    private PDFGenerationService pdfGenerationService = new PDFGenerationService();

    public void generateCV(CVData cvData, String filePath) throws IOException {
        CV cv = new CV();
        cv.setName(cvData.getFullName());
        cv.setEmail(cvData.getEmail());
        cv.setPhone(cvData.getPhone());
        cv.setAddress(cvData.getAddress());
        cv.setLinkedin(cvData.getLinkedin());
        cv.setPortfolio(cvData.getPortfolio());
        cv.setSummary(cvData.getSummary());
        cv.setEducations(cvData.getEducations());
        cv.setWorkExperiences(cvData.getWorkExperiences());
        cv.setProjects(cvData.getProjects());
        cv.setCertificates(cvData.getCertificates());
        cv.setLanguages(cvData.getLanguages());
        cv.setSkills(cvData.getSkills());

        pdfGenerationService.generatePDF(cv, filePath);
    }
}