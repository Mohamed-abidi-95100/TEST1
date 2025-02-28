package com.example.stage.service;

import com.example.stage.model.CV;
import com.example.stage.model.Certificate;
import com.example.stage.model.Education;
import com.example.stage.model.Language;
import com.example.stage.model.Project;
import com.example.stage.model.WorkExperience;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_BOLD;

public class PDFGenerationService {

    public void generateCVPDF(CV cv, String filePath) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.beginText();
            contentStream.setFont(HELVETICA_BOLD, 18);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Curriculum Vitae");
            contentStream.endText();

            int yOffset = 730;

            yOffset = addPersonalInformation(contentStream, cv, yOffset);
            yOffset = addSection(contentStream, "Summary", cv.getSummary(), yOffset);
            yOffset = addEducationSection(contentStream, cv.getEducations(), yOffset);
            yOffset = addWorkExperienceSection(contentStream, cv.getWorkExperiences(), yOffset);
            yOffset = addProjectsSection(contentStream, cv.getProjects(), yOffset);
            yOffset = addCertificatesSection(contentStream, cv.getCertificates(), yOffset);
            yOffset = addLanguagesSection(contentStream, cv.getLanguages(), yOffset);
            addSkillsSection(contentStream, cv.getSkills(), yOffset);
        }

        document.save(filePath);
        document.close();
    }

    private int addPersonalInformation(PDPageContentStream contentStream, CV cv, int yOffset) throws IOException {
        contentStream.beginText();
        contentStream.setFont(HELVETICA, 12);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Name: " + cv.getName());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Email: " + cv.getEmail());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Phone: " + cv.getPhone());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Address: " + cv.getAddress());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("LinkedIn: " + cv.getLinkedin());
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("Portfolio: " + cv.getPortfolio());
        contentStream.endText();
        return yOffset - 90;
    }

    private int addSection(PDPageContentStream contentStream, String title, String content, int yOffset) throws IOException {
        contentStream.beginText();
        contentStream.setFont(HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText(title);
        contentStream.endText();

        yOffset -= 20;

        if (content != null && !content.isEmpty()) {
            contentStream.beginText();
            contentStream.setFont(HELVETICA, 12);
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(content);
            contentStream.endText();
            yOffset -= 20;
        }
        return yOffset;
    }

    private int addEducationSection(PDPageContentStream contentStream, List<Education> educations, int yOffset) throws IOException {
        contentStream.beginText();
        contentStream.setFont(HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Education");
        contentStream.endText();

        yOffset -= 20;
        contentStream.setFont(HELVETICA, 12);
        for (Education education : educations) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(education.getDegree() + ", " + education.getInstitution() + ", " + education.getGraduationDate());
            contentStream.endText();
            yOffset -= 20;
        }
        return yOffset;
    }

    private int addWorkExperienceSection(PDPageContentStream contentStream, List<WorkExperience> workExperiences, int yOffset) throws IOException {
        contentStream.beginText();
        contentStream.setFont(HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Work Experience");
        contentStream.endText();

        yOffset -= 20;
        contentStream.setFont(HELVETICA, 12);
        for (WorkExperience workExperience : workExperiences) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(workExperience.getJobTitle() + ", " + workExperience.getCompany() + ", " + workExperience.getDescription());
            contentStream.endText();
            yOffset -= 20;
        }
        return yOffset;
    }

    private int addProjectsSection(PDPageContentStream contentStream, List<Project> projects, int yOffset) throws IOException {
        contentStream.beginText();
        contentStream.setFont(HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Projects");
        contentStream.endText();

        yOffset -= 20;
        contentStream.setFont(HELVETICA, 12);
        for (Project project : projects) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(project.getProjectName() + ", " + project.getDescription());
            contentStream.endText();
            yOffset -= 20;
        }
        return yOffset;
    }

    private int addCertificatesSection(PDPageContentStream contentStream, List<Certificate> certificates, int yOffset) throws IOException {
        contentStream.beginText();
        contentStream.setFont(HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Certificates");
        contentStream.endText();

        yOffset -= 20;
        contentStream.setFont(HELVETICA, 12);
        for (Certificate certificate : certificates) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(certificate.getName() + ", " + certificate.getInstitution() + ", " + certificate.getDate());
            contentStream.endText();
            yOffset -= 20;
        }
        return yOffset;
    }

    private int addLanguagesSection(PDPageContentStream contentStream, List<Language> languages, int yOffset) throws IOException {
        contentStream.beginText();
        contentStream.setFont(HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Languages");
        contentStream.endText();

        yOffset -= 20;
        contentStream.setFont(HELVETICA, 12);
        for (Language language : languages) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(language.getName() + ", " + language.getProficiency());
            contentStream.endText();
            yOffset -= 20;
        }
        return yOffset;
    }

    private void addSkillsSection(PDPageContentStream contentStream, List<String> skills, int yOffset) throws IOException {
        contentStream.beginText();
        contentStream.setFont(HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Skills");
        contentStream.endText();

        yOffset -= 20;
        contentStream.setFont(HELVETICA, 12);
        for (String skill : skills) {
            contentStream.beginText();
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(skill);
            contentStream.endText();
            yOffset -= 20;
        }
    }
}