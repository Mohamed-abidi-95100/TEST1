package com.example.stage.service;

import com.example.stage.model.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFGenerationService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void generatePDF(CV cv, String filePath) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setLeading(14.5f);

            // Add header
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Curriculum Vitae");
            contentStream.endText();
            contentStream.addRect(50, 745, 500, 1);
            contentStream.stroke();

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
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, yOffset);

        if (cv.getName() != null) {
            contentStream.showText("Name: " + cv.getName());
            contentStream.newLine();
        }

        if (cv.getEmail() != null) {
            contentStream.showText("Email: " + cv.getEmail());
            contentStream.newLine();
        }

        if (cv.getPhone() != null) {
            contentStream.showText("Phone: " + cv.getPhone());
            contentStream.newLine();
        }

        if (cv.getAddress() != null) {
            contentStream.showText("Address: " + cv.getAddress());
            contentStream.newLine();
        }

        if (cv.getLinkedin() != null) {
            contentStream.showText("LinkedIn: " + cv.getLinkedin());
            contentStream.newLine();
        }

        if (cv.getPortfolio() != null) {
            contentStream.showText("Portfolio: " + cv.getPortfolio());
            contentStream.newLine();
        }

        contentStream.endText();
        return yOffset - 80;
    }

    private int addSection(PDPageContentStream contentStream, String title, String content, int yOffset) throws IOException {
        if (content == null || content.isEmpty()) {
            return yOffset;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText(title);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        contentStream.newLineAtOffset(50, yOffset - 15);
        contentStream.showText(content);
        contentStream.endText();

        return yOffset - 30;
    }

    private int addEducationSection(PDPageContentStream contentStream, List<Education> educations, int yOffset) throws IOException {
        if (educations.isEmpty()) {
            return yOffset;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Education");
        contentStream.endText();
        yOffset -= 15;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        for (Education education : educations) {
            contentStream.newLineAtOffset(50, yOffset);
            String graduationDate = education.getGraduationDate() != null ?
                    education.getGraduationDate().format(DATE_FORMATTER) : "Present";
            contentStream.showText(education.getDegree() + " - " + education.getInstitution() +
                    " (" + graduationDate + ")");
            contentStream.newLine();
            yOffset -= 15;
        }
        contentStream.endText();
        return yOffset - 15;
    }

    private int addWorkExperienceSection(PDPageContentStream contentStream, List<WorkExperience> workExperiences, int yOffset) throws IOException {
        if (workExperiences.isEmpty()) {
            return yOffset;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Work Experience");
        contentStream.endText();
        yOffset -= 15;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        for (WorkExperience workExperience : workExperiences) {
            contentStream.newLineAtOffset(50, yOffset);
            String endDate = workExperience.getEndDate() != null ?
                    workExperience.getEndDate().format(DATE_FORMATTER) : "Present";
            contentStream.showText(workExperience.getJobTitle() + " - " +
                    workExperience.getCompany() + " (" +
                    workExperience.getStartDate().format(DATE_FORMATTER) +
                    " - " + endDate + ")");
            contentStream.newLineAtOffset(0, -15);
            if (workExperience.getDescription() != null) {
                contentStream.showText(workExperience.getDescription());
            }
            yOffset -= 30;
        }
        contentStream.endText();
        return yOffset - 15;
    }

    private int addProjectsSection(PDPageContentStream contentStream, List<Project> projects, int yOffset) throws IOException {
        if (projects.isEmpty()) {
            return yOffset;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Projects");
        contentStream.endText();
        yOffset -= 15;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        for (Project project : projects) {
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(project.getName());
            contentStream.newLineAtOffset(0, -15);
            if (project.getDescription() != null) {
                contentStream.showText(project.getDescription());
            }
            yOffset -= 30;
        }
        contentStream.endText();
        return yOffset - 15;
    }

    private int addCertificatesSection(PDPageContentStream contentStream, List<Certificate> certificates, int yOffset) throws IOException {
        if (certificates.isEmpty()) {
            return yOffset;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Certificates");
        contentStream.endText();
        yOffset -= 15;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        for (Certificate certificate : certificates) {
            contentStream.newLineAtOffset(50, yOffset);
            String dateReceived = certificate.getDateReceived() != null ?
                    certificate.getDateReceived().format(DATE_FORMATTER) : "";
            contentStream.showText(certificate.getName() + " - " +
                    certificate.getInstitution() +
                    (dateReceived.isEmpty() ? "" : " (" + dateReceived + ")"));
            contentStream.newLine();
            yOffset -= 15;
        }
        contentStream.endText();
        return yOffset - 15;
    }

    private int addLanguagesSection(PDPageContentStream contentStream, List<Language> languages, int yOffset) throws IOException {
        if (languages.isEmpty()) {
            return yOffset;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Languages");
        contentStream.endText();
        yOffset -= 15;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        for (Language language : languages) {
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(language.getName() + " - " +
                    language.getProficiencyLevel().getDescription());
            contentStream.newLine();
            yOffset -= 15;
        }
        contentStream.endText();
        return yOffset - 15;
    }

    private void addSkillsSection(PDPageContentStream contentStream, List<String> skills, int yOffset) throws IOException {
        if (skills.isEmpty()) {
            return;
        }

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
        contentStream.newLineAtOffset(50, yOffset);
        contentStream.showText("Skills");
        contentStream.endText();
        yOffset -= 15;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        for (String skill : skills) {
            contentStream.newLineAtOffset(50, yOffset);
            contentStream.showText(skill);
            contentStream.newLine();
            yOffset -= 15;
        }
        contentStream.endText();
    }
}