package com.example.stage.model;

public class WorkExperience {
    private String jobTitle;
    private String company;
    private String startDate;
    private String endDate;
    private String description;

    public WorkExperience(String jobTitle, String company) {
        this.jobTitle = jobTitle;
        this.company = company;
    }

    public WorkExperience(String jobTitle, String company, String startDate, String endDate, String description) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public WorkExperience(String jobTitle, String company, String description) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.description = description;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "WorkExperience{" +
                "jobTitle='" + jobTitle + '\'' +
                ", company='" + company + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}