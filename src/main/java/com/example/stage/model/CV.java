package com.example.stage.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CV {
    // Attributs privés
    private String name;
    private String email;
    private String phone;
    private String address;
    private String linkedin;
    private String portfolio;
    private String summary;
    private String profileType;
    private LocalDateTime lastUpdated;
    private List<Education> educations;
    private List<WorkExperience> workExperiences;
    private List<Project> projects;
    private List<Certificate> certificates;
    private List<Language> languages;
    private List<String> skills;

    // Constructeur par défaut
    public CV() {
        this.educations = new ArrayList<>();
        this.workExperiences = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.certificates = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.lastUpdated = LocalDateTime.now();
    }

    // Constructeur avec paramètres
    public CV(String name, String email, String phone, String address) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        updateLastModified();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        updateLastModified();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        updateLastModified();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        updateLastModified();
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
        updateLastModified();
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
        updateLastModified();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
        updateLastModified();
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
        updateLastModified();
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public List<Education> getEducations() {
        return new ArrayList<>(educations);
    }

    public void setEducations(List<Education> educations) {
        this.educations = new ArrayList<>(educations);
        updateLastModified();
    }

    public List<WorkExperience> getWorkExperiences() {
        return new ArrayList<>(workExperiences);
    }

    public void setWorkExperiences(List<WorkExperience> workExperiences) {
        this.workExperiences = new ArrayList<>(workExperiences);
        updateLastModified();
    }

    public List<Project> getProjects() {
        return new ArrayList<>(projects);
    }

    public void setProjects(List<Project> projects) {
        this.projects = new ArrayList<>(projects);
        updateLastModified();
    }

    public List<Certificate> getCertificates() {
        return new ArrayList<>(certificates);
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = new ArrayList<>(certificates);
        updateLastModified();
    }

    public List<Language> getLanguages() {
        return new ArrayList<>(languages);
    }

    public void setLanguages(List<Language> languages) {
        this.languages = new ArrayList<>(languages);
        updateLastModified();
    }

    public List<String> getSkills() {
        return new ArrayList<>(skills);
    }

    public void setSkills(List<String> skills) {
        this.skills = new ArrayList<>(skills);
        updateLastModified();
    }

    // Méthodes pour ajouter des éléments
    public void addEducation(Education education) {
        if (education != null) {
            this.educations.add(education);
            updateLastModified();
        }
    }

    public void addWorkExperience(WorkExperience experience) {
        if (experience != null) {
            this.workExperiences.add(experience);
            updateLastModified();
        }
    }

    public void addProject(Project project) {
        if (project != null) {
            this.projects.add(project);
            updateLastModified();
        }
    }

    public void addCertificate(Certificate certificate) {
        if (certificate != null) {
            this.certificates.add(certificate);
            updateLastModified();
        }
    }

    public void addLanguage(Language language) {
        if (language != null) {
            this.languages.add(language);
            updateLastModified();
        }
    }

    public void addSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            this.skills.add(skill.trim());
            updateLastModified();
        }
    }

    // Méthodes pour supprimer des éléments
    public void removeEducation(Education education) {
        if (this.educations.remove(education)) {
            updateLastModified();
        }
    }

    public void removeWorkExperience(WorkExperience experience) {
        if (this.workExperiences.remove(experience)) {
            updateLastModified();
        }
    }

    public void removeProject(Project project) {
        if (this.projects.remove(project)) {
            updateLastModified();
        }
    }

    public void removeCertificate(Certificate certificate) {
        if (this.certificates.remove(certificate)) {
            updateLastModified();
        }
    }

    public void removeLanguage(Language language) {
        if (this.languages.remove(language)) {
            updateLastModified();
        }
    }

    public void removeSkill(String skill) {
        if (this.skills.remove(skill)) {
            updateLastModified();
        }
    }

    // Méthode utilitaire pour mettre à jour la date de dernière modification
    private void updateLastModified() {
        this.lastUpdated = LocalDateTime.now();
    }

    // Méthodes de validation
    public boolean isValid() {
        return name != null && !name.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                phone != null && !phone.trim().isEmpty() &&
                address != null && !address.trim().isEmpty() &&
                !educations.isEmpty() &&
                !workExperiences.isEmpty() &&
                !skills.isEmpty();
    }

    // Override des méthodes equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CV cv = (CV) o;
        return Objects.equals(email, cv.email) &&
                Objects.equals(phone, cv.phone) &&
                Objects.equals(name, cv.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, phone, name);
    }

    // Override de toString pour afficher les informations du CV
    @Override
    public String toString() {
        return "CV{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", numberOfEducations=" + educations.size() +
                ", numberOfWorkExperiences=" + workExperiences.size() +
                ", numberOfSkills=" + skills.size() +
                ", lastUpdated=" + lastUpdated +
                '}';
    }

    // Méthode pour créer une copie profonde du CV
    public CV clone() {
        CV clone = new CV();
        clone.setName(this.name);
        clone.setEmail(this.email);
        clone.setPhone(this.phone);
        clone.setAddress(this.address);
        clone.setLinkedin(this.linkedin);
        clone.setPortfolio(this.portfolio);
        clone.setSummary(this.summary);
        clone.setProfileType(this.profileType);
        clone.setEducations(new ArrayList<>(this.educations));
        clone.setWorkExperiences(new ArrayList<>(this.workExperiences));
        clone.setProjects(new ArrayList<>(this.projects));
        clone.setCertificates(new ArrayList<>(this.certificates));
        clone.setLanguages(new ArrayList<>(this.languages));
        clone.setSkills(new ArrayList<>(this.skills));
        return clone;
    }
}