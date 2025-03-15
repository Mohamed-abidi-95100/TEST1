package com.example.stage.controller;

import com.example.stage.model.*;
import com.example.stage.service.PDFGenerationService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Page2Controller {
    // FXML Injected Fields
    @FXML private VBox skillsContainer;
    @FXML private TextField skillsField;
    @FXML private VBox projectsContainer;
    @FXML private VBox certificatesContainer;
    @FXML private VBox languagesContainer;

    @FXML private TextField projectNameField;
    @FXML private TextArea projectDescriptionArea;
    @FXML private TextField certificateNameField;
    @FXML private TextField certificateInstitutionField;
    @FXML private DatePicker certificateDateField;
    @FXML private TextField languageField;
    @FXML private ComboBox<String> languageProficiencyField;
    @FXML private ComboBox<String> languageComboBox;

    @FXML private Button backButton;
    @FXML private Button generatePDFButton;
    @FXML private Button translateButton;
    @FXML private Button autoFillButton;
    @FXML private Label statusLabel;

    // Constants
    private static final int MAX_SKILLS = 10;
    private static final int MAX_PROJECTS = 5;
    private static final int MAX_CERTIFICATES = 5;
    private static final int MAX_LANGUAGES = 5;
    private static final int MAX_DESCRIPTION_LENGTH = 500;
    private static final String ERROR_STYLE = "-fx-border-color: red;";
    private static final String NORMAL_STYLE = "";

    // Services and Models
    private final PDFGenerationService pdfGenerationService;
    private CV cv;

    // Constructor
    public Page2Controller() {
        this.pdfGenerationService = new PDFGenerationService();
        this.cv = new CV();
    }

    @FXML
    private void initialize() {
        System.out.println("Initializing Page2Controller");
        try {
            setupContainers();
            setupLanguageProficiencyComboBox();
            setupValidations();
            setupProjectDescriptionLimit();
            logUIComponentState();
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Setup Methods
    private void setupContainers() {
        if (skillsContainer == null) skillsContainer = new VBox(5);
        if (projectsContainer == null) projectsContainer = new VBox(5);
        if (certificatesContainer == null) certificatesContainer = new VBox(5);
        if (languagesContainer == null) languagesContainer = new VBox(5);
    }

    private void setupLanguageProficiencyComboBox() {
        if (languageProficiencyField != null) {
            languageProficiencyField.setItems(FXCollections.observableArrayList(
                    "A1 (Beginner)",
                    "A2 (Elementary)",
                    "B1 (Intermediate)",
                    "B2 (Upper Intermediate)",
                    "C1 (Advanced)",
                    "C2 (Mastery)"
            ));
        }
    }

    private void setupValidations() {
        if (skillsField != null) {
            skillsField.textProperty().addListener((obs, old, newVal) -> {
                if (skillsContainer.getChildren().size() >= MAX_SKILLS) {
                    showAlert(Alert.AlertType.WARNING, "Maximum Skills",
                            "You can add up to " + MAX_SKILLS + " skills.");
                    skillsField.setText(old);
                }
            });
        }

        if (certificateDateField != null) {
            certificateDateField.valueProperty().addListener((obs, old, newVal) -> {
                if (newVal != null && newVal.isAfter(LocalDate.now())) {
                    showAlert(Alert.AlertType.WARNING, "Invalid Date",
                            "Certificate date cannot be in the future.");
                    certificateDateField.setValue(old);
                }
            });
        }
    }

    private void setupProjectDescriptionLimit() {
        if (projectDescriptionArea != null) {
            projectDescriptionArea.textProperty().addListener((obs, old, newVal) -> {
                if (newVal.length() > MAX_DESCRIPTION_LENGTH) {
                    projectDescriptionArea.setText(old);
                    showAlert(Alert.AlertType.WARNING, "Text Limit",
                            "Project description cannot exceed " + MAX_DESCRIPTION_LENGTH + " characters.");
                }
            });
        }
    }

    // Add Methods
    @FXML
    private void addSkill() {
        String skill = skillsField.getText().trim();
        if (skill.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Skill cannot be empty.");
            return;
        }

        if (skillsContainer.getChildren().size() >= MAX_SKILLS) {
            showAlert(Alert.AlertType.WARNING, "Limit Reached",
                    "Maximum number of skills (" + MAX_SKILLS + ") reached.");
            return;
        }

        HBox skillEntry = new HBox(10);
        Label skillLabel = new Label(skill);
        Button removeButton = createRemoveButton(skillEntry);
        skillEntry.getChildren().addAll(skillLabel, removeButton);
        skillsContainer.getChildren().add(skillEntry);
        skillsField.clear();
    }

    @FXML
    private void addProject() {
        if (!validateProjectFields()) {
            return;
        }

        if (projectsContainer.getChildren().size() >= MAX_PROJECTS) {
            showAlert(Alert.AlertType.WARNING, "Limit Reached",
                    "Maximum number of projects (" + MAX_PROJECTS + ") reached.");
            return;
        }

        GridPane projectEntry = new GridPane();
        projectEntry.setHgap(10);
        projectEntry.setVgap(10);

        Label nameLabel = new Label(projectNameField.getText());
        TextArea descArea = new TextArea(projectDescriptionArea.getText());
        descArea.setWrapText(true);
        descArea.setPrefRowCount(2);
        descArea.setEditable(false);

        projectEntry.add(new Label("Project:"), 0, 0);
        projectEntry.add(nameLabel, 1, 0);
        projectEntry.add(new Label("Description:"), 0, 1);
        projectEntry.add(descArea, 1, 1);

        Button removeButton = createRemoveButton(projectEntry);
        projectEntry.add(removeButton, 1, 2);

        projectsContainer.getChildren().add(projectEntry);
        clearProjectFields();
    }

    @FXML
    private void addCertificate() {
        if (!validateCertificateFields()) {
            return;
        }

        if (certificatesContainer.getChildren().size() >= MAX_CERTIFICATES) {
            showAlert(Alert.AlertType.WARNING, "Limit Reached",
                    "Maximum number of certificates (" + MAX_CERTIFICATES + ") reached.");
            return;
        }

        GridPane certificateEntry = new GridPane();
        certificateEntry.setHgap(10);
        certificateEntry.setVgap(10);

        Label nameLabel = new Label(certificateNameField.getText());
        Label institutionLabel = new Label(certificateInstitutionField.getText());
        Label dateLabel = new Label(certificateDateField.getValue().toString());

        certificateEntry.add(new Label("Certificate:"), 0, 0);
        certificateEntry.add(nameLabel, 1, 0);
        certificateEntry.add(new Label("Institution:"), 0, 1);
        certificateEntry.add(institutionLabel, 1, 1);
        certificateEntry.add(new Label("Date:"), 0, 2);
        certificateEntry.add(dateLabel, 1, 2);

        Button removeButton = createRemoveButton(certificateEntry);
        certificateEntry.add(removeButton, 1, 3);

        certificatesContainer.getChildren().add(certificateEntry);
        clearCertificateFields();
    }

    @FXML
    private void addLanguage() {
        if (!validateLanguageFields()) {
            return;
        }

        if (languagesContainer.getChildren().size() >= MAX_LANGUAGES) {
            showAlert(Alert.AlertType.WARNING, "Limit Reached",
                    "Maximum number of languages (" + MAX_LANGUAGES + ") reached.");
            return;
        }

        GridPane languageEntry = new GridPane();
        languageEntry.setHgap(10);
        languageEntry.setVgap(10);

        Label langLabel = new Label(languageField.getText());
        Label profLabel = new Label(languageProficiencyField.getValue());

        languageEntry.add(new Label("Language:"), 0, 0);
        languageEntry.add(langLabel, 1, 0);
        languageEntry.add(new Label("Proficiency:"), 0, 1);
        languageEntry.add(profLabel, 1, 1);

        Button removeButton = createRemoveButton(languageEntry);
        languageEntry.add(removeButton, 1, 2);

        languagesContainer.getChildren().add(languageEntry);
        clearLanguageFields();
    }

    // UI Helper Methods
    private Button createRemoveButton(Node parent) {
        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #E31A22; -fx-text-fill: white;");
        removeButton.setOnAction(e -> ((VBox) parent.getParent()).getChildren().remove(parent));
        return removeButton;
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Validation Methods
    private boolean validateProjectFields() {
        if (projectNameField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Project name is required.");
            return false;
        }
        if (projectDescriptionArea.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Project description is required.");
            return false;
        }
        return true;
    }

    private boolean validateCertificateFields() {
        if (certificateNameField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Certificate name is required.");
            return false;
        }
        if (certificateInstitutionField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Institution name is required.");
            return false;
        }
        if (certificateDateField.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Certificate date is required.");
            return false;
        }
        return true;
    }

    private boolean validateLanguageFields() {
        if (languageField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Language name is required.");
            return false;
        }
        if (languageProficiencyField.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Language proficiency is required.");
            return false;
        }
        return true;
    }

    private boolean validateAllFields() {
        if (skillsContainer.getChildren().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "At least one skill is required.");
            return false;
        }
        if (projectsContainer.getChildren().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "At least one project is required.");
            return false;
        }
        return true;
    }

    // Clear Methods
    private void clearProjectFields() {
        projectNameField.clear();
        projectDescriptionArea.clear();
    }

    private void clearCertificateFields() {
        certificateNameField.clear();
        certificateInstitutionField.clear();
        certificateDateField.setValue(null);
    }

    private void clearLanguageFields() {
        languageField.clear();
        languageProficiencyField.setValue(null);
    }

    private void clearAllFields() {
        skillsContainer.getChildren().clear();
        skillsField.clear();
        projectsContainer.getChildren().clear();
        clearProjectFields();
        certificatesContainer.getChildren().clear();
        clearCertificateFields();
        languagesContainer.getChildren().clear();
        clearLanguageFields();
    }

    // Auto-Fill Feature
    @FXML
    public void autoFillForm(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            clearAllFields();

            // Add sample skills
            String[] sampleSkills = {
                    "Java Programming", "JavaFX", "Spring Boot",
                    "Git", "SQL", "Problem Solving",
                    "Web Development", "RESTful APIs",
                    "Software Design", "Agile Methodologies"
            };

            for (String skill : sampleSkills) {
                skillsField.setText(skill);
                addSkill();
            }

            // Add sample projects
            Project[] sampleProjects = {
                    new Project("CV Builder Application",
                            "Developed a full-featured CV management system using JavaFX. " +
                                    "Implemented PDF generation, form validation, and data persistence."),
                    new Project("E-Commerce Platform",
                            "Built a scalable e-commerce solution with Spring Boot and MySQL. " +
                                    "Features include user authentication and product management.")
            };

            for (Project project : sampleProjects) {
                projectNameField.setText(project.getName());
                projectDescriptionArea.setText(project.getDescription());
                addProject();
            }

            // Add sample certificates
            Object[][] sampleCertificates = {
                    {"Oracle Certified Professional: Java SE 11", "Oracle", LocalDate.now().minusMonths(6)},
                    {"AWS Certified Developer - Associate", "Amazon Web Services", LocalDate.now().minusMonths(3)},
                    {"Spring Framework Certification", "VMware", LocalDate.now().minusMonths(1)}
            };

            for (Object[] cert : sampleCertificates) {
                certificateNameField.setText((String)cert[0]);
                certificateInstitutionField.setText((String)cert[1]);
                certificateDateField.setValue((LocalDate)cert[2]);
                addCertificate();
            }

            // Add sample languages
            Object[][] sampleLanguages = {
                    {"English", "C1 (Advanced)"},
                    {"French", "B2 (Upper Intermediate)"},
                    {"Arabic", "C2 (Mastery)"}
            };

            for (Object[] lang : sampleLanguages) {
                languageField.setText((String)lang[0]);
                languageProficiencyField.setValue((String)lang[1]);
                addLanguage();
            }
        });
    }

    // Navigation Methods
    @FXML
    public void goToPage1(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/page1.fxml"));
            AnchorPane page1 = loader.load();
            Scene scene = new Scene(page1, 900, 600);
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error",
                    "Could not return to previous page: " + e.getMessage());
        }
    }

    // Help Methods
    @FXML
    public void showProjectHelp(ActionEvent actionEvent) {
        showAlert(Alert.AlertType.INFORMATION, "Project Help",
                "Projects should include:\n" +
                        "- A clear, concise name\n" +
                        "- Detailed description (max 500 characters)\n" +
                        "- Key achievements and technologies used\n" +
                        "- Your role and responsibilities\n\n" +
                        "Tips:\n" +
                        "- Use action verbs to start descriptions\n" +
                        "- Include measurable outcomes\n" +
                        "- Highlight technical skills used"
        );
    }

    @FXML
    public void showSkillsHelp(ActionEvent actionEvent) {
        showAlert(Alert.AlertType.INFORMATION, "Skills Help",
                "Skills tips:\n" +
                        "- List relevant technical skills\n" +
                        "- Include both hard and soft skills\n" +
                        "- Be specific (e.g., 'Java' instead of 'Programming')\n" +
                        "- List skills you're comfortable discussing\n" +
                        "- Maximum 10 skills allowed\n\n" +
                        "Click 'Auto Fill' to see example skills."
        );
    }

    // PDF Generation
    @FXML
    private void generatePDF() {
        if (!validateAllFields()) {
            return;
        }

        try {
            updateCVModel();
            File outputDir = new File("output");
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
            String filePath = "output/cv_" + System.currentTimeMillis() + ".pdf";
            pdfGenerationService.generatePDF(cv, filePath);
            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "CV has been generated successfully at: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to generate PDF: " + e.getMessage());
        }
    }

    // Translation Feature
    @FXML
    public void translatePage(ActionEvent actionEvent) {
        showAlert(Alert.AlertType.INFORMATION, "Translation",
                "Translation feature coming soon!\n" +
                        "This will allow you to translate your CV content to different languages.");
    }

    // Model Update Methods
    private void updateCVModel() {
        // Update skills
        cv.getSkills().clear();
        skillsContainer.getChildren().forEach(node -> {
            if (node instanceof HBox) {
                Label skillLabel = (Label) ((HBox) node).getChildren().get(0);
                cv.addSkill(skillLabel.getText());
            }
        });

        // Update projects
        cv.getProjects().clear();
        projectsContainer.getChildren().forEach(node -> {
            if (node instanceof GridPane) {
                GridPane grid = (GridPane) node;
                Label nameLabel = (Label) getNodeByRowColumn(grid, 0, 1);
                TextArea descArea = (TextArea) getNodeByRowColumn(grid, 1, 1);

                if (nameLabel != null && descArea != null) {
                    Project project = new Project(
                            nameLabel.getText(),
                            descArea.getText()
                    );
                    cv.addProject(project);
                }
            }
        });

        // Update certificates
        cv.getCertificates().clear();
        certificatesContainer.getChildren().forEach(node -> {
            if (node instanceof GridPane) {
                GridPane grid = (GridPane) node;
                Label nameLabel = (Label) getNodeByRowColumn(grid, 0, 1);
                Label institutionLabel = (Label) getNodeByRowColumn(grid, 1, 1);
                Label dateLabel = (Label) getNodeByRowColumn(grid, 2, 1);

                if (nameLabel != null && institutionLabel != null && dateLabel != null) {
                    Certificate certificate = new Certificate(
                            nameLabel.getText(),
                            institutionLabel.getText(),
                            LocalDate.parse(dateLabel.getText())
                    );
                    cv.addCertificate(certificate);
                }
            }
        });

        // Update languages
        cv.getLanguages().clear();
        languagesContainer.getChildren().forEach(node -> {
            if (node instanceof GridPane) {
                GridPane grid = (GridPane) node;
                Label langLabel = (Label) getNodeByRowColumn(grid, 0, 1);
                Label profLabel = (Label) getNodeByRowColumn(grid, 1, 1);

                if (langLabel != null && profLabel != null) {
                    String proficiencyText = profLabel.getText();
                    Language.ProficiencyLevel level = Language.ProficiencyLevel.valueOf(
                            proficiencyText.substring(0, 2)  // Get "A1", "B2", etc.
                    );
                    Language language = new Language(langLabel.getText(), level);
                    cv.addLanguage(language);
                }
            }
        });
    }

    // Data Loading Methods
    public void setCV(CV cv) {
        this.cv = cv;
        loadCVData();
    }

    private void loadCVData() {
        if (cv == null) {
            return;
        }

        Platform.runLater(() -> {
            // Load skills
            cv.getSkills().forEach(skill -> {
                HBox skillEntry = new HBox(10);
                Label skillLabel = new Label(skill);
                Button removeButton = createRemoveButton(skillEntry);
                skillEntry.getChildren().addAll(skillLabel, removeButton);
                skillsContainer.getChildren().add(skillEntry);
            });

            // Load projects
            cv.getProjects().forEach(project -> {
                GridPane projectEntry = new GridPane();
                projectEntry.setHgap(10);
                projectEntry.setVgap(10);

                Label nameLabel = new Label(project.getName());
                TextArea descArea = new TextArea(project.getDescription());
                descArea.setWrapText(true);
                descArea.setPrefRowCount(2);
                descArea.setEditable(false);

                projectEntry.add(new Label("Project:"), 0, 0);
                projectEntry.add(nameLabel, 1, 0);
                projectEntry.add(new Label("Description:"), 0, 1);
                projectEntry.add(descArea, 1, 1);

                Button removeButton = createRemoveButton(projectEntry);
                projectEntry.add(removeButton, 1, 2);

                projectsContainer.getChildren().add(projectEntry);
            });

            // Load certificates and languages similarly
            loadCertificatesAndLanguages();
        });
    }

    private void loadCertificatesAndLanguages() {
        // Load certificates
        cv.getCertificates().forEach(certificate -> {
            GridPane certificateEntry = createCertificateEntry(certificate);
            certificatesContainer.getChildren().add(certificateEntry);
        });

        // Load languages
        cv.getLanguages().forEach(language -> {
            GridPane languageEntry = createLanguageEntry(language);
            languagesContainer.getChildren().add(languageEntry);
        });
    }

    private GridPane createCertificateEntry(Certificate certificate) {
        GridPane entry = new GridPane();
        entry.setHgap(10);
        entry.setVgap(10);

        entry.add(new Label("Certificate:"), 0, 0);
        entry.add(new Label(certificate.getName()), 1, 0);
        entry.add(new Label("Institution:"), 0, 1);
        entry.add(new Label(certificate.getInstitution()), 1, 1);
        entry.add(new Label("Date:"), 0, 2);
        entry.add(new Label(certificate.getDateReceived().toString()), 1, 2);

        Button removeButton = createRemoveButton(entry);
        entry.add(removeButton, 1, 3);

        return entry;
    }

    private GridPane createLanguageEntry(Language language) {
        GridPane entry = new GridPane();
        entry.setHgap(10);
        entry.setVgap(10);

        entry.add(new Label("Language:"), 0, 0);
        entry.add(new Label(language.getName()), 1, 0);
        entry.add(new Label("Proficiency:"), 0, 1);
        entry.add(new Label(language.getProficiencyDescription()), 1, 1);

        Button removeButton = createRemoveButton(entry);
        entry.add(removeButton, 1, 2);

        return entry;
    }

    private Node getNodeByRowColumn(GridPane gridPane, int row, int column) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    private void logUIComponentState() {
        System.out.println("UI Component State:");
        System.out.println("skillsField: " + (skillsField != null ? "initialized" : "null"));
        System.out.println("skillsContainer: " + (skillsContainer != null ? "initialized" : "null"));
        System.out.println("projectNameField: " + (projectNameField != null ? "initialized" : "null"));
        System.out.println("projectDescriptionArea: " + (projectDescriptionArea != null ? "initialized" : "null"));
        System.out.println("certificateNameField: " + (certificateNameField != null ? "initialized" : "null"));
        System.out.println("languageField: " + (languageField != null ? "initialized" : "null"));
        System.out.println("languageProficiencyField: " + (languageProficiencyField != null ? "initialized" : "null"));
    }
}