package com.example.stage.controller;

import com.example.stage.model.CV;
import com.example.stage.model.Education;
import com.example.stage.model.WorkExperience;
import com.example.stage.util.ResourceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
public class Page1Controller {
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextArea addressArea;
    @FXML private TextField linkedinField;
    @FXML private TextField portfolioField;
    @FXML private TextArea summaryArea;

    @FXML private TextField degreeField;
    @FXML private TextField institutionField;
    @FXML private DatePicker graduationDateField;
    @FXML private VBox educationContainer;

    @FXML private TextField jobTitleField;
    @FXML private TextField companyField;
    @FXML private DatePicker startDateField;
    @FXML private DatePicker endDateField;
    @FXML private TextArea descriptionArea;
    @FXML private VBox workExperienceContainer;

    @FXML private Button nextButton;
    @FXML private Button autoFillButton;
    @FXML private ComboBox<String> languageComboBox;
    @FXML private Button translateButton;

    @FXML private Label personalInformationLabel;
    @FXML private Label fullNameLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label addressLabel;
    @FXML private Label linkedinLabel;
    @FXML private Label portfolioLabel;
    @FXML private Label summaryObjectiveLabel;
    @FXML private Label educationLabel;
    @FXML private Label workExperienceLabel;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^\\+?[0-9]{8,}$";
    private static final String LINKEDIN_REGEX = "^(https?://)?(www\\.)?linkedin\\.com/.*$";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final int MAX_SUMMARY_LENGTH = 500;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;


    @FXML
    private void initialize() {
        setupLanguageComboBox();
        setupValidations();
        setupInitialValues();
    }

    private void setupLanguageComboBox() {
        languageComboBox.getItems().addAll("English", "French", "German", "Arabic");
        languageComboBox.setValue("English");
    }

    private void setupValidations() {
        emailField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateEmail();
        });

        phoneField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validatePhone();
        });

        linkedinField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateLinkedIn();
        });

        summaryArea.textProperty().addListener((obs, old, newVal) -> {
            if (newVal.length() > MAX_SUMMARY_LENGTH) {
                summaryArea.setText(old);
                showAlert("Warning", "Summary cannot exceed " + MAX_SUMMARY_LENGTH + " characters.");
            }
        });

        descriptionArea.textProperty().addListener((obs, old, newVal) -> {
            if (newVal.length() > MAX_DESCRIPTION_LENGTH) {
                descriptionArea.setText(old);
                showAlert("Warning", "Description cannot exceed " + MAX_DESCRIPTION_LENGTH + " characters.");
            }
        });
    }

    private void setupInitialValues() {
        nameField.setPromptText("Enter your full name");
        emailField.setPromptText("example@email.com");
        phoneField.setPromptText("+21612345678");
        addressArea.setPromptText("Enter your address");
        linkedinField.setPromptText("linkedin.com/in/yourprofile");
        portfolioField.setPromptText("yourportfolio.com");
        summaryArea.setPromptText("Brief professional summary");
    }

    @FXML
    private void translatePage() {
        String selectedLanguage = languageComboBox.getValue();
        Locale locale = switch (selectedLanguage) {
            case "French" -> Locale.FRENCH;
            case "German" -> Locale.GERMAN;
            case "Arabic" -> Locale.forLanguageTag("ar"); // Changed from new Locale("ar")
            default -> Locale.ENGLISH;
        };
        ResourceManager.setLocale(locale);
        updateTranslations();
    }
    private void updateTranslations() {
        personalInformationLabel.setText(ResourceManager.getString("personal_information"));
        fullNameLabel.setText(ResourceManager.getString("full_name"));
        emailLabel.setText(ResourceManager.getString("email"));
        phoneLabel.setText(ResourceManager.getString("phone"));
        addressLabel.setText(ResourceManager.getString("address"));
        linkedinLabel.setText(ResourceManager.getString("linkedin"));
        portfolioLabel.setText(ResourceManager.getString("portfolio"));
        summaryObjectiveLabel.setText(ResourceManager.getString("summary_objective"));
        educationLabel.setText(ResourceManager.getString("education"));
        workExperienceLabel.setText(ResourceManager.getString("work_experience"));

        nextButton.setText(ResourceManager.getString("next"));
        translateButton.setText(ResourceManager.getString("translate"));
        autoFillButton.setText(ResourceManager.getString("auto_fill"));
    }

    @FXML
    private void addEducation() {
        if (!validateEducationFields()) return;

        GridPane educationEntry = new GridPane();
        educationEntry.setHgap(10);
        educationEntry.setVgap(10);

        // Create fields
        TextField degreeField = new TextField(this.degreeField.getText());
        TextField institutionField = new TextField(this.institutionField.getText());
        DatePicker graduationDateField = new DatePicker(this.graduationDateField.getValue());

        // Set column constraints
        educationEntry.getColumnConstraints().addAll(
                new ColumnConstraints(106),
                new ColumnConstraints(156)
        );

        // Add fields
        educationEntry.add(new Label("Degree:"), 0, 0);
        educationEntry.add(degreeField, 1, 0);
        educationEntry.add(new Label("Institution:"), 0, 1);
        educationEntry.add(institutionField, 1, 1);
        educationEntry.add(new Label("Graduation Date:"), 0, 2);
        educationEntry.add(graduationDateField, 1, 2);

        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #E31A22; -fx-text-fill: white;");
        removeButton.setOnAction(e -> educationContainer.getChildren().remove(educationEntry));
        educationEntry.add(removeButton, 1, 3);

        educationContainer.getChildren().add(educationEntry);
        clearEducationFields();
    }

    @FXML
    private void addWorkExperience() {
        if (!validateWorkExperienceFields()) return;

        GridPane workExperienceEntry = new GridPane();
        workExperienceEntry.setHgap(10);
        workExperienceEntry.setVgap(10);

        // Create fields
        TextField jobTitleField = new TextField(this.jobTitleField.getText());
        TextField companyField = new TextField(this.companyField.getText());
        DatePicker startDateField = new DatePicker(this.startDateField.getValue());
        DatePicker endDateField = new DatePicker(this.endDateField.getValue());
        TextArea descriptionArea = new TextArea(this.descriptionArea.getText());
        descriptionArea.setPrefHeight(60);
        descriptionArea.setWrapText(true);

        // Set column constraints
        workExperienceEntry.getColumnConstraints().addAll(
                new ColumnConstraints(106),
                new ColumnConstraints(156)
        );

        // Add fields
        workExperienceEntry.add(new Label("Job Title:"), 0, 0);
        workExperienceEntry.add(jobTitleField, 1, 0);
        workExperienceEntry.add(new Label("Company:"), 0, 1);
        workExperienceEntry.add(companyField, 1, 1);
        workExperienceEntry.add(new Label("Start Date:"), 0, 2);
        workExperienceEntry.add(startDateField, 1, 2);
        workExperienceEntry.add(new Label("End Date:"), 0, 3);
        workExperienceEntry.add(endDateField, 1, 3);
        workExperienceEntry.add(new Label("Description:"), 0, 4);
        workExperienceEntry.add(descriptionArea, 1, 4);

        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #E31A22; -fx-text-fill: white;");
        removeButton.setOnAction(e -> workExperienceContainer.getChildren().remove(workExperienceEntry));
        workExperienceEntry.add(removeButton, 1, 5);

        workExperienceContainer.getChildren().add(workExperienceEntry);
        clearWorkExperienceFields();
    }

    private boolean validateEmail() {
        String email = emailField.getText().trim();
        if (!email.matches(EMAIL_REGEX)) {
            emailField.setStyle("-fx-border-color: red;");
            showAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }
        emailField.setStyle("");
        return true;
    }

    private boolean validatePhone() {
        String phone = phoneField.getText().trim();
        if (!phone.matches(PHONE_REGEX)) {
            phoneField.setStyle("-fx-border-color: red;");
            showAlert("Invalid Phone", "Please enter a valid phone number.");
            return false;
        }
        phoneField.setStyle("");
        return true;
    }

    private boolean validateLinkedIn() {
        String linkedin = linkedinField.getText().trim();
        if (!linkedin.isEmpty() && !linkedin.matches(LINKEDIN_REGEX)) {
            linkedinField.setStyle("-fx-border-color: red;");
            showAlert("Invalid LinkedIn", "Please enter a valid LinkedIn URL.");
            return false;
        }
        linkedinField.setStyle("");
        return true;
    }

    private boolean validateEducationFields() {
        if (degreeField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Degree field is required.");
            return false;
        }
        if (institutionField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Institution field is required.");
            return false;
        }
        if (graduationDateField.getValue() == null) {
            showAlert("Validation Error", "Graduation date is required.");
            return false;
        }
        if (graduationDateField.getValue().isAfter(LocalDate.now())) {
            showAlert("Validation Error", "Graduation date cannot be in the future.");
            return false;
        }
        return true;
    }

    private boolean validateWorkExperienceFields() {
        if (jobTitleField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Job title is required.");
            return false;
        }
        if (companyField.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Company name is required.");
            return false;
        }
        if (startDateField.getValue() == null) {
            showAlert("Validation Error", "Start date is required.");
            return false;
        }
        if (endDateField.getValue() != null &&
                endDateField.getValue().isBefore(startDateField.getValue())) {
            showAlert("Validation Error", "End date cannot be before start date.");
            return false;
        }
        if (startDateField.getValue().isAfter(LocalDate.now())) {
            showAlert("Validation Error", "Start date cannot be in the future.");
            return false;
        }
        if (descriptionArea.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Job description is required.");
            return false;
        }
        return true;
    }

    @FXML
    private void autoFillForm() {
        nameField.setText("Mohamed Abidi");
        emailField.setText("mohamed.abidi@example.com");
        phoneField.setText("+21612345678");
        addressArea.setText("123 Rue de Tunis, Tunis, Tunisia");
        linkedinField.setText("linkedin.com/in/mohamedabidi");
        portfolioField.setText("mohamedabidi.com");
        summaryArea.setText("Experienced software engineer with a strong background in developing scalable web applications.");

        degreeField.setText("Computer Science Engineering");
        institutionField.setText("ESPRIM");
        graduationDateField.setValue(LocalDate.now().minusYears(1));
        addEducation();

        jobTitleField.setText("Software Engineer");
        companyField.setText("Tech Company");
        startDateField.setValue(LocalDate.now().minusYears(2));
        endDateField.setValue(LocalDate.now());
        descriptionArea.setText("Developed and maintained enterprise web applications using Java and Spring Boot.");
        addWorkExperience();
    }

    private void clearEducationFields() {
        degreeField.clear();
        institutionField.clear();
        graduationDateField.setValue(null);
    }

    private void clearWorkExperienceFields() {
        jobTitleField.clear();
        companyField.clear();
        startDateField.setValue(null);
        endDateField.setValue(null);
        descriptionArea.clear();
    }

    @FXML
    private void showSummaryHelp() {
        showAlert("Summary Help",
                "Write a brief professional summary highlighting your key qualifications, " +
                        "experience, and career objectives. Keep it concise and impactful.");
    }

    @FXML
    private void goToPage2() {
        if (validateAllFields()) {
            try {
                CV cv = getData();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/stage/page2.fxml"));
                AnchorPane page2 = loader.load();
                Page2Controller controller = loader.getController();
                controller.setCV(cv);

                Scene scene = new Scene(page2, 600, 900);
                Stage stage = (Stage) nextButton.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Could not load the next page.");
            }
        }
    }
    private boolean validateAllFields() {
        ArrayList<String> errors = new ArrayList<>();

        if (nameField.getText().trim().isEmpty()) {
            errors.add("Name is required");
        }
        if (!validateEmail()) {
            errors.add("Invalid email format");
        }
        if (!validatePhone()) {
            errors.add("Invalid phone format");
        }
        if (!validateLinkedIn()) {
            errors.add("Invalid LinkedIn URL format");
        }
        if (addressArea.getText().trim().isEmpty()) {
            errors.add("Address is required");
        }
        if (summaryArea.getText().trim().isEmpty()) {
            errors.add("Professional summary is required");
        }
        if (educationContainer.getChildren().isEmpty()) {
            errors.add("At least one education entry is required");
        }
        if (workExperienceContainer.getChildren().isEmpty()) {
            errors.add("At least one work experience entry is required");
        }

        if (!errors.isEmpty()) {
            showAlert("Validation Errors", String.join("\n", errors));
            return false;
        }

        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setData(CV cv) {
        if (cv != null) {
            nameField.setText(cv.getName());
            emailField.setText(cv.getEmail());
            phoneField.setText(cv.getPhone());
            addressArea.setText(cv.getAddress());
            linkedinField.setText(cv.getLinkedin());
            portfolioField.setText(cv.getPortfolio());
            summaryArea.setText(cv.getSummary());

            // Load education entries
            cv.getEducations().forEach(education -> {
                degreeField.setText(education.getDegree());
                institutionField.setText(education.getInstitution());
                graduationDateField.setValue(education.getGraduationDate());
                addEducation();
            });

            // Load work experience entries
            cv.getWorkExperiences().forEach(experience -> {
                jobTitleField.setText(experience.getJobTitle());
                companyField.setText(experience.getCompany());
                if (experience.getStartDate() != null) {
                    startDateField.setValue(experience.getStartDate());
                }
                if (experience.getEndDate() != null) {
                    endDateField.setValue(experience.getEndDate());
                }
                descriptionArea.setText(experience.getDescription());
                addWorkExperience();
            });
        }
    }

    public CV getData() {
        CV cv = new CV();
        cv.setName(nameField.getText().trim());
        cv.setEmail(emailField.getText().trim());
        cv.setPhone(phoneField.getText().trim());
        cv.setAddress(addressArea.getText().trim());
        cv.setLinkedin(linkedinField.getText().trim());
        cv.setPortfolio(portfolioField.getText().trim());
        cv.setSummary(summaryArea.getText().trim());

        // Get education entries
        educationContainer.getChildren().forEach(node -> {
            if (node instanceof GridPane) {
                GridPane grid = (GridPane) node;
                TextField degreeField = (TextField) getNodeByRowColumn(grid, 0, 1);
                TextField institutionField = (TextField) getNodeByRowColumn(grid, 1, 1);
                DatePicker graduationDatePicker = (DatePicker) getNodeByRowColumn(grid, 2, 1);

                if (degreeField != null && institutionField != null && graduationDatePicker != null) {
                    String degree = degreeField.getText().trim();
                    String institution = institutionField.getText().trim();
                    LocalDate graduationDate = graduationDatePicker.getValue();

                    if (!degree.isEmpty() && !institution.isEmpty() && graduationDate != null) {
                        Education education = new Education(degree, institution, graduationDate);
                        cv.addEducation(education);
                    }
                }
            }
        });

        // Get work experience entries with validation
        workExperienceContainer.getChildren().forEach(node -> {
            if (node instanceof GridPane) {
                GridPane grid = (GridPane) node;
                TextField jobTitleField = (TextField) getNodeByRowColumn(grid, 0, 1);
                TextField companyField = (TextField) getNodeByRowColumn(grid, 1, 1);
                DatePicker startDatePicker = (DatePicker) getNodeByRowColumn(grid, 2, 1);
                DatePicker endDatePicker = (DatePicker) getNodeByRowColumn(grid, 3, 1);
                TextArea descriptionArea = (TextArea) getNodeByRowColumn(grid, 4, 1);

                // Add debug logging
                System.out.println("Processing work experience entry");
                System.out.println("Job Title Field: " + (jobTitleField != null ? jobTitleField.getText() : "null"));
                System.out.println("Company Field: " + (companyField != null ? companyField.getText() : "null"));

                if (jobTitleField != null && companyField != null) {
                    String jobTitle = jobTitleField.getText().trim();
                    String company = companyField.getText().trim();

                    if (!jobTitle.isEmpty() && !company.isEmpty()) {
                        try {
                            WorkExperience experience = new WorkExperience(jobTitle, company);

                            if (startDatePicker != null && startDatePicker.getValue() != null) {
                                experience.setStartDate(startDatePicker.getValue());
                            }

                            if (endDatePicker != null && endDatePicker.getValue() != null) {
                                experience.setEndDate(endDatePicker.getValue());
                            }

                            if (descriptionArea != null && !descriptionArea.getText().trim().isEmpty()) {
                                experience.setDescription(descriptionArea.getText().trim());
                            }

                            cv.addWorkExperience(experience);
                        } catch (IllegalArgumentException e) {
                            System.err.println("Error creating work experience: " + e.getMessage());
                            // Optionally show an alert to the user
                            showAlert("Error", "Invalid work experience entry: " + e.getMessage());
                        }
                    }
                }
            }
        });

        return cv;
    }


    private Node getNodeByRowColumn(GridPane gridPane, int row, int column) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer columnIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && columnIndex != null &&
                    rowIndex == row && columnIndex == column) {
                return node;
            }
        }
        return null;
    }
}