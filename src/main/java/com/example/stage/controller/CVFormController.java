package com.example.stage.controller;

import com.example.stage.model.CV;
import com.example.stage.model.Certificate;
import com.example.stage.model.Education;
import com.example.stage.model.Language;
import com.example.stage.model.Project;
import com.example.stage.model.WorkExperience;
import com.example.stage.service.CVImprovementService;
import com.example.stage.service.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class CVFormController {

  /*  @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextArea addressArea;
    @FXML
    private TextField linkedinField;
    @FXML
    private TextField portfolioField;
    @FXML
    private TextArea summaryArea;
    @FXML
    private VBox educationContainer;
    @FXML
    private VBox workExperienceContainer;
    @FXML
    private TextField skillsField;
    @FXML
    private VBox projectsContainer;
    @FXML
    private VBox certificatesContainer;
    @FXML
    private VBox languagesContainer;
    @FXML
    private ComboBox<String> templateComboBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label suggestionsLabel;

    private CV cv = new CV();
    private UserService userService = new UserService();
    private CVImprovementService cvImprovementService = new CVImprovementService();

    @FXML
    private void initialize() {
        loadUserData();
        loadCVTemplates();

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.addEventFilter(ScrollEvent.SCROLL, event -> {
            double deltaY = event.getDeltaY() * 0.01;
            scrollPane.setVvalue(scrollPane.getVvalue() - deltaY / scrollPane.getContent().getBoundsInLocal().getHeight());
        });
    }

    private void loadUserData() {
        nameField.setText(userService.getUserName());
        emailField.setText(userService.getUserEmail());
        phoneField.setText(userService.getUserPhone());
        addressArea.setText(userService.getUserAddress());
        linkedinField.setText(userService.getUserLinkedIn());
        portfolioField.setText(userService.getUserPortfolio());
        summaryArea.setText(userService.getUserSummary());
    }

    private void loadCVTemplates() {
        templateComboBox.getItems().addAll("Template 1", "Template 2", "Template 3");
    }

    @FXML
    private void addEducation() {GridPane educationEntry = new GridPane();
        educationEntry.setHgap(5);
        educationEntry.setVgap(5);

        TextField degreeField = new TextField();
        TextField institutionField = new TextField("ESPRIM");
        DatePicker graduationDateField = new DatePicker();

        educationEntry.add(new Label("Degree:"), 0, 0);
        educationEntry.add(degreeField, 1, 0);
        educationEntry.add(new Label("Institution:"), 0, 1);
        educationEntry.add(institutionField, 1, 1);
        educationEntry.add(new Label("Graduation Date:"), 0, 2);
        educationEntry.add(graduationDateField, 1, 2);

        Button removeButton = new Button(   "Remove");
        removeButton.setOnAction(e -> {
            educationContainer.getChildren().remove(educationEntry);
        });
        educationEntry.add(removeButton, 1, 3);

        educationContainer.getChildren().add(educationEntry);

        //  Update the CV object:
        String degree = degreeField.getText();
        String institution = institutionField.getText();
        LocalDate graduationDate = graduationDateField.getValue();

        Education education = new Education(degree, institution, graduationDate);
        cv.getEducations().add(education);
    }

    @FXML
    private void addWorkExperience() {
        GridPane workExperienceEntry = new GridPane();
        workExperienceEntry.setHgap(5);
        workExperienceEntry.setVgap(5);

        TextField jobTitleField = new TextField();
        TextField companyField = new TextField();
        TextArea descriptionArea = new TextArea();

        workExperienceEntry.add(new Label("Job Title:"), 0, 0);
        workExperienceEntry.add(jobTitleField, 1, 0);
        workExperienceEntry.add(new Label("Company:"), 0, 1);
        workExperienceEntry.add(companyField, 1, 1);
        workExperienceEntry.add(new Label("Description:"), 0, 2);
        workExperienceEntry.add(descriptionArea, 1, 2);

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            workExperienceContainer.getChildren().remove(workExperienceEntry);
        });
        workExperienceEntry.add(removeButton, 1, 3);

        workExperienceContainer.getChildren().add(workExperienceEntry);
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
        projectEntry.add(projectDescriptionArea, 1, 1);

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            projectsContainer.getChildren().remove(projectEntry);
        });
        projectEntry.add(removeButton, 1, 2);

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

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            certificatesContainer.getChildren().remove(certificateEntry);
        });
        certificateEntry.add(removeButton, 1, 3);

        certificatesContainer.getChildren().add(certificateEntry);
    }

    @FXML
    private void addLanguage() {
        GridPane languageEntry = new GridPane();
        languageEntry.setHgap(5);
        languageEntry.setVgap(5);

        TextField languageField = new TextField();
        ComboBox<String> languageProficiencyField = new ComboBox<>(FXCollections.observableArrayList("Beginner", "Intermediate", "Advanced", "Bilingual"));

        languageEntry.add(new Label("Language:"), 0, 0);
        languageEntry.add(languageField, 1, 0);
        languageEntry.add(new Label("Proficiency Level:"), 0, 1);
        languageEntry.add(languageProficiencyField, 1, 1);

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            languagesContainer.getChildren().remove(languageEntry);
        });
        languageEntry.add(removeButton, 1, 2);

        languagesContainer.getChildren().add(languageEntry);
    }

    @FXML
    private void generateCV() {
        System.out.println("Generating CV...");

        // Gather all data from the form and update the cv object. This is crucial!
        cv.setName(nameField.getText());
        cv.setEmail(emailField.getText());
        cv.setPhone(phoneField.getText());
        cv.setAddress(addressArea.getText());
        cv.setLinkedIn(linkedinField.getText());
        cv.setPortfolio(portfolioField.getText());
        cv.setSummary(summaryArea.getText());
        cv.setSkills(Collections.singletonList(skillsField.getText())); // Add skills

        // Gather Education, Work Experience, Projects, Certificates, and Languages
        cv.getEducations().clear(); // Clear existing data before adding new ones
        for (Node node : educationContainer.getChildren()) {
            if (node instanceof GridPane) {
                GridPane gridPane = (GridPane) node;
                TextField degree = (TextField) gridPane.getChildren().get(1); // Assuming TextField is at index 1
                TextField institution = (TextField) gridPane.getChildren().get(3); // Assuming TextField is at index 3
                DatePicker graduationDate = (DatePicker) gridPane.getChildren().get(5); // Assuming DatePicker is at index 5

                if (degree != null && institution != null && graduationDate != null) {
                    cv.getEducations().add(new Education(degree.getText(), institution.getText(), graduationDate.getValue()));
                }
            }
        }

        cv.getWorkExperiences().clear();
        for (Node node : workExperienceContainer.getChildren()) {
            if (node instanceof GridPane) {
                GridPane gridPane = (GridPane) node;
                TextField jobTitle = (TextField) gridPane.getChildren().get(1);
                TextField company = (TextField) gridPane.getChildren().get(3);
                TextArea description = (TextArea) gridPane.getChildren().get(5);

                if (jobTitle != null && company != null && description != null) {
                    cv.getWorkExperiences().add(new WorkExperience(jobTitle.getText(), company.getText(), description.getText()));
                }
            }
        }

        cv.getProjects().clear();
        for (Node node : projectsContainer.getChildren()) {
            if (node instanceof GridPane) {
                GridPane gridPane = (GridPane) node;
                TextField projectName = (TextField) gridPane.getChildren().get(1);
                TextArea projectDescription = (TextArea) gridPane.getChildren().get(3);

                if (projectName != null && projectDescription != null) {
                    cv.getProjects().add(new Project(projectName.getText(), projectDescription.getText()));
                }
            }
        }

        cv.getCertificates().clear();
        for (Node node : certificatesContainer.getChildren()) {
            if (node instanceof GridPane) {
                GridPane gridPane = (GridPane) node;
                TextField certificateName = (TextField) gridPane.getChildren().get(1);
                TextField certificateInstitution = (TextField) gridPane.getChildren().get(3);
                DatePicker certificateDate = (DatePicker) gridPane.getChildren().get(5);

                if (certificateName != null && certificateInstitution != null && certificateDate != null) {
                    cv.getCertificates().add(new Certificate(certificateName.getText(), certificateInstitution.getText(), certificateDate.getValue()));
                }
            }
        }

        cv.getLanguages().clear();
        for (Node node : languagesContainer.getChildren()) {
            if (node instanceof GridPane) {
                GridPane gridPane = (GridPane) node;
                TextField language = (TextField) gridPane.getChildren().get(1);
                ComboBox<String> proficiency = (ComboBox<String>) gridPane.getChildren().get(3);

                if (language != null && proficiency != null) {
                    cv.getLanguages().add(new Language(language.getText(), proficiency.getValue()));
                }
            }
        }

        System.out.println(cv); // Now cv should have all the data

        String selectedTemplate = templateComboBox.getValue();
        if (selectedTemplate != null) {
            System.out.println("Using template: " + selectedTemplate);
            // Call your CV generation logic here, passing the 'cv' object and the selected template.
        } else {
            System.out.println("No template selected");
        }
    }

    @FXML
    private void improveCV() {
        List<String> suggestions = cvImprovementService.getSuggestions(cv);
        suggestionsLabel.setText(String.join("\n", suggestions));
    }

    @FXML
    private void handleFullScreen(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }*/
}