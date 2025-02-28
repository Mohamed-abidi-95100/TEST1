package com.example.stage.controller;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Page1Controller {

    public TextField degreeField;
    public TextField institutionField;
    public DatePicker graduationDateField;
    public TextField jobTitleField;
    public TextField companyField;
    public TextArea descriptionArea;
    @FXML
    private Button nextButton;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private VBox educationContainer;
    @FXML
    private VBox workExperienceContainer;

    // Labels and Buttons to be translated
    @FXML
    private Label personalInformationLabel;
    @FXML
    private Label fullNameLabel; // Ensure this is declared
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label linkedinLabel;
    @FXML
    private Label portfolioLabel;
    @FXML
    private Label summaryObjectiveLabel;
    @FXML
    private Label educationLabel;
    @FXML
    private Label workExperienceLabel;
    @FXML
    private Button addEducationButton;
    @FXML
    private Button addWorkExperienceButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button autoFillButton;

    @FXML
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
    private void initialize() {
        languageComboBox.getItems().addAll("English", "French", "German", "Arabic");
        languageComboBox.setOnAction(e -> translatePage());
        loadTranslations(Locale.ENGLISH);
        autoFillButton.setOnAction(event -> autoFillForm());
    }

    @FXML
    private void goToPage2() throws IOException {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        AnchorPane page2 = FXMLLoader.load(getClass().getResource("/com/example/stage/page2.fxml"));

        Scene scene = new Scene(page2, 600, 900);
        stage.setScene(scene);
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
                locale = Locale.forLanguageTag("ar");
                break;
            default:
                locale = Locale.ENGLISH;
                break;
        }
        loadTranslations(locale);
    }

    private void loadTranslations(Locale locale) {
        ResourceBundle bundle = getBundle("messages", locale);
        if (bundle != null) {
            personalInformationLabel.setText(bundle.getString("personal_information"));
            fullNameLabel.setText(bundle.getString("full_name"));
            emailLabel.setText(bundle.getString("email"));
            phoneLabel.setText(bundle.getString("phone"));
            addressLabel.setText(bundle.getString("address"));
            linkedinLabel.setText(bundle.getString("linkedin"));
            portfolioLabel.setText(bundle.getString("portfolio"));
            summaryObjectiveLabel.setText(bundle.getString("summary_objective"));
            educationLabel.setText(bundle.getString("education"));
            workExperienceLabel.setText(bundle.getString("work_experience"));
            addEducationButton.setText(bundle.getString("add_education"));
            addWorkExperienceButton.setText(bundle.getString("add_experience"));
            nextButton.setText(bundle.getString("next"));
            translateButton.setText(bundle.getString("translate"));
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
    private void addEducation() {
        GridPane educationEntry = new GridPane();
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

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> educationContainer.getChildren().remove(educationEntry));
        educationEntry.add(removeButton, 1, 3);

        educationContainer.getChildren().add(educationEntry);
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
        removeButton.setOnAction(e -> workExperienceContainer.getChildren().remove(workExperienceEntry));
        workExperienceEntry.add(removeButton, 1, 3);

        workExperienceContainer.getChildren().add(workExperienceEntry);
    }

    @FXML
    private void showSummaryHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Summary Help");
        alert.setHeaderText(null);
        alert.setContentText("Please provide a brief summary or objective for your CV.");
        alert.showAndWait();
    }

    @FXML
    private void showDescriptionHelp() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Description Help");
        alert.setHeaderText(null);
        alert.setContentText("Please provide a description of your role, responsibilities, and achievements.");
        alert.showAndWait();
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

        // Add example education
        educationContainer.getChildren().clear();
        for (int i = 1; i <= 2; i++) {
            addEducation();
            GridPane educationEntry = (GridPane) educationContainer.getChildren().get(i-1);
            TextField degreeField = (TextField) educationEntry.getChildren().get(1);
            TextField institutionField = (TextField) educationEntry.getChildren().get(3);
            DatePicker graduationDateField = (DatePicker) educationEntry.getChildren().get(5);
            degreeField.setText("Degree " + i);
            institutionField.setText("University " + i);
            graduationDateField.setValue(java.time.LocalDate.of(2020, 1, i));
        }

        // Add example work experience
        workExperienceContainer.getChildren().clear();
        for (int i = 1; i <= 2; i++) {
            addWorkExperience();
            GridPane workExperienceEntry = (GridPane) workExperienceContainer.getChildren().get(i-1);
            TextField jobTitleField = (TextField) workExperienceEntry.getChildren().get(1);
            TextField companyField = (TextField) workExperienceEntry.getChildren().get(3);
            TextArea descriptionArea = (TextArea) workExperienceEntry.getChildren().get(5);
            jobTitleField.setText("Job Title " + i);
            companyField.setText("Company " + i);
            descriptionArea.setText("Description of job " + i);
        }
    }
}