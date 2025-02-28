package com.example.stage.controller;

import com.example.stage.model.CV;
import com.example.stage.model.Certificate;
import com.example.stage.model.Education;
import com.example.stage.model.Language;
import com.example.stage.model.Project;
import com.example.stage.service.PDFGenerationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Page2Controller {

    @FXML
    private TextField skillsField;
    @FXML
    private VBox projectsContainer;
    @FXML
    private VBox certificatesContainer;
    @FXML
    private VBox languagesContainer;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private Button translateButton;
    @FXML
    private Button generatePDFButton;
    @FXML
    private Button autoFillButton;

    // Labels to be translated
    @FXML
    private Label skillsLabel;
    @FXML
    private Label projectsLabel;
    @FXML
    private Label certificatesLabel;
    @FXML
    private Label languagesLabel;

    private PDFGenerationService pdfGenerationService = new PDFGenerationService();

    @FXML
    private void initialize() {
        languageComboBox.getItems().addAll("English", "French", "German", "Arabic");
        languageComboBox.setOnAction(e -> translatePage());
        loadTranslations(Locale.ENGLISH);
        autoFillButton.setOnAction(event -> autoFillForm());
    }

    private void loadTranslations(Locale locale) {
        ResourceBundle bundle = getBundle("messages", locale);
        if (bundle != null) {
            skillsLabel.setText(bundle.getString("skills"));
            projectsLabel.setText(bundle.getString("projects"));
            certificatesLabel.setText(bundle.getString("certificates"));
            languagesLabel.setText(bundle.getString("languages"));
            backButton.setText(bundle.getString("back"));
            translateButton.setText(bundle.getString("translate"));
            generatePDFButton.setText(bundle.getString("generate_pdf"));
            autoFillButton.setText(bundle.getString("auto_fill"));
        } else {
            System.err.println("Resource bundle is null for locale: " + locale);
        }
    }

    private ResourceBundle getBundle(String baseName, Locale locale) {
        String resourceName = baseName + "_" + locale.getLanguage() + ".properties";
        String resourcePath = "/com/example/stage/" + resourceName;
        try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(resourcePath), StandardCharsets.UTF_8)) {
            if (reader != null) {
                return new PropertyResourceBundle(reader);
            } else {
                System.err.println("Resource not found: " + resourcePath);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    private void addProject() {
        GridPane projectEntry = new GridPane();
        projectEntry.setHgap(5);
        projectEntry.setVgap(5);

        TextField projectNameField = new TextField();
        TextArea projectDescriptionArea = new TextArea();

        projectEntry.add(new Label("Project Name:"), 0, 0);
        projectEntry.add(projectNameField, 1, 0);
        projectEntry.add(new Label("Project Description:"), 0, 1);
        HBox descriptionBox = new HBox();
        descriptionBox.getChildren().addAll(projectDescriptionArea, createHelpButton("#showProjectHelp"));
        projectEntry.add(descriptionBox, 1, 1);
        projectEntry.add(createRemoveButton(projectEntry), 1, 2);

        projectsContainer.getChildren().add(projectEntry);
    }

    @FXML
    private void addCertificate() {
        GridPane certificateEntry = new GridPane();
        certificateEntry.setHgap(5);
        certificateEntry.setVgap(5);

        TextField certificateNameField = new TextField();
        TextField certificateInstitutionField = new TextField();
        DatePicker certificateDateField = new DatePicker();

        certificateEntry.add(new Label("Certificate Name:"), 0, 0);
        certificateEntry.add(certificateNameField, 1, 0);
        certificateEntry.add(new Label("Institution:"), 0, 1);
        certificateEntry.add(certificateInstitutionField, 1, 1);
        certificateEntry.add(new Label("Date of Receipt:"), 0, 2);
        certificateEntry.add(certificateDateField, 1, 2);
        certificateEntry.add(createRemoveButton(certificateEntry), 1, 3);

        certificatesContainer.getChildren().add(certificateEntry);
    }

    @FXML
    private void addLanguage() {
        GridPane languageEntry = new GridPane();
        languageEntry.setHgap(5);
        languageEntry.setVgap(5);

        TextField languageField = new TextField();
        ComboBox<String> languageProficiencyField = new ComboBox<>();
        languageProficiencyField.getItems().addAll("Beginner", "Intermediate", "Advanced", "Bilingual");

        languageEntry.add(new Label("Language:"), 0, 0);
        languageEntry.add(languageField, 1, 0);
        languageEntry.add(new Label("Proficiency Level:"), 0, 1);
        languageEntry.add(languageProficiencyField, 1, 1);
        languageEntry.add(createRemoveButton(languageEntry), 1, 2);

        languagesContainer.getChildren().add(languageEntry);
    }

    @FXML
    private void showSkillsHelp() {
        showAlert(Alert.AlertType.INFORMATION, "Skills Help", "Please provide a list of your skills. Include any technical skills, soft skills, or other relevant abilities.");
    }

    @FXML
    private void showProjectHelp() {
        showAlert(Alert.AlertType.INFORMATION, "Project Help", "Please provide details about your projects. Include the project name, description, and any key achievements or technologies used.");
    }

    @FXML
    private void showCertificateHelp() {
        showAlert(Alert.AlertType.INFORMATION, "Certificate Help", "Please provide details about your certificates. Include the certificate name, institution, and date of receipt.");
    }

    @FXML
    private void showLanguageHelp() {
        showAlert(Alert.AlertType.INFORMATION, "Language Help", "Please provide details about your language skills. Include the language name and proficiency level.");
    }

    @FXML
    private void goToPage1() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            AnchorPane page1 = FXMLLoader.load(getClass().getResource("/com/example/stage/page1.fxml"));

            Scene scene = new Scene(page1, 600, 900);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void translatePage() {
        String selectedLanguage = languageComboBox.getValue();
        Locale locale;
        switch (selectedLanguage) {
            case "French":
                locale = Locale.FRENCH;
                break;
            case "German":
                locale = Locale.GERMAN;
                break;
            case "Arabic":
                locale = new Locale("ar");
                break;
            default:
                locale = Locale.ENGLISH;
                break;
        }
        loadTranslations(locale);
    }

    @FXML
    private void generatePDF(ActionEvent event) {
        CV cv = new CV();

        // Collect skills
        List<String> skills = new ArrayList<>();
        for (String skill : skillsField.getText().split(",")) {
            skills.add(skill.trim());
        }
        cv.setSkills(skills);

        // Collect projects
        for (int i = 0; i < projectsContainer.getChildren().size(); i++) {
            GridPane projectEntry = (GridPane) projectsContainer.getChildren().get(i);
            TextField projectNameField = (TextField) projectEntry.getChildren().get(1);
            TextArea projectDescriptionArea = (TextArea) ((HBox) projectEntry.getChildren().get(3)).getChildren().get(0);
            Project project = new Project(projectNameField.getText(), projectDescriptionArea.getText());
            cv.getProjects().add(project);
        }

        // Collect certificates
        for (int i = 0; i < certificatesContainer.getChildren().size(); i++) {
            GridPane certificateEntry = (GridPane) certificatesContainer.getChildren().get(i);
            TextField certificateNameField = (TextField) certificateEntry.getChildren().get(1);
            TextField certificateInstitutionField = (TextField) certificateEntry.getChildren().get(3);
            DatePicker certificateDateField = (DatePicker) certificateEntry.getChildren().get(5);
            Certificate certificate = new Certificate(certificateNameField.getText(), certificateInstitutionField.getText(), certificateDateField.getValue());
            cv.getCertificates().add(certificate);
        }

        // Collect languages
        for (int i = 0; i < languagesContainer.getChildren().size(); i++) {
            GridPane languageEntry = (GridPane) languagesContainer.getChildren().get(i);
            TextField languageField = (TextField) languageEntry.getChildren().get(1);
            ComboBox<String> proficiencyField = (ComboBox<String>) languageEntry.getChildren().get(3);
            Language language = new Language(languageField.getText(), proficiencyField.getValue());
            cv.getLanguages().add(language);
        }

        // Dummy data for other sections
        cv.getEducations().add(new Education("Bachelor of Science", "University A", "2020"));
        cv.getWorkExperiences().add(new com.example.stage.model.WorkExperience("Software Engineer", "Company B", "Developed various applications"));

        try {
            // Ensure the output directory exists
            File outputDir = new File("output");
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }
            // Specify the path where the PDF will be saved
            String filePath = "output/cv.pdf";
            pdfGenerationService.generateCVPDF(cv, filePath);
            showAlert(Alert.AlertType.INFORMATION, "Success", "CV PDF has been generated successfully. You can find it at: " + filePath);
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while generating the CV PDF.");
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Button createHelpButton(String action) {
        Button helpButton = new Button("?");
        helpButton.setStyle("-fx-background-color: #E31A22; -fx-text-fill: white; -fx-font-weight: bold;");
        helpButton.setOnAction(event -> {
            try {
                getClass().getDeclaredMethod(action).invoke(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return helpButton;
    }

    private Button createRemoveButton(GridPane entry) {
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #E31A22; -fx-text-fill: white;");
        removeButton.setOnAction(event -> {
            ((VBox) entry.getParent()).getChildren().remove(entry);
        });
        return removeButton;
    }

    @FXML
    private void autoFillForm() {
        skillsField.setText("Java, Python, SQL, HTML, CSS");

        // Add example projects
        projectsContainer.getChildren().clear();
        for (int i = 1; i <= 2; i++) {
            addProject();
            GridPane projectEntry = (GridPane) projectsContainer.getChildren().get(i-1);
            TextField projectNameField = (TextField) projectEntry.getChildren().get(1);
            TextArea projectDescriptionArea = (TextArea) ((HBox) projectEntry.getChildren().get(3)).getChildren().get(0);
            projectNameField.setText("Project " + i);
            projectDescriptionArea.setText("Description of project " + i);
        }

        // Add example certificates
        certificatesContainer.getChildren().clear();
        for (int i = 1; i <= 2; i++) {
            addCertificate();
            GridPane certificateEntry = (GridPane) certificatesContainer.getChildren().get(i-1);
            TextField certificateNameField = (TextField) certificateEntry.getChildren().get(1);
            TextField certificateInstitutionField = (TextField) certificateEntry.getChildren().get(3);
            DatePicker certificateDateField = (DatePicker) certificateEntry.getChildren().get(5);
            certificateNameField.setText("Certificate " + i);
            certificateInstitutionField.setText("Institution " + i);
            certificateDateField.setValue(java.time.LocalDate.of(2020, 1, i));
        }

        // Add example languages
        languagesContainer.getChildren().clear();
        for (int i = 1; i <= 2; i++) {
            addLanguage();
            GridPane languageEntry = (GridPane) languagesContainer.getChildren().get(i-1);
            TextField languageField = (TextField) languageEntry.getChildren().get(1);
            ComboBox<String> proficiencyField = (ComboBox<String>) languageEntry.getChildren().get(3);
            languageField.setText("Language " + i);
            proficiencyField.setValue("Intermediate");
        }
    }
}