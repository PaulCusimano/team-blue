package com.teamblue.safetyapp.Models;

import java.time.LocalDateTime;

public class Report {

    // Instance variables
    private String reportType;
    private String reportName;
    private Location location;
    private LocalDateTime incidentDateTime;
    private LocalDateTime reportDateTime;
    private String reportDescription;
    private String status;

    // Constructor
    public Report(String type, String name, Location location, LocalDateTime incidentTime, LocalDateTime reportTime,
            String reportDescription, String status) {
        this.reportType = type;
        this.reportName = name;
        this.location = location;
        this.incidentDateTime = incidentTime;
        this.reportDateTime = reportTime;
        this.reportDescription = reportDescription;
        this.status = status;
    }

    // Getter and setter methods for each instance variable
    public String getReportType() {
        return reportType;
    }

    public void setReportType(String type) {
        this.reportType = type;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String name) {
        this.reportName = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getIncidentDateTime() {
        return incidentDateTime;
    }

    public void setIncidentDateTime(LocalDateTime incidentTime) {
        this.incidentDateTime = incidentTime;
    }

    public LocalDateTime getReportDateTime() {
        return reportDateTime;
    }

    public void setReportDateTime(LocalDateTime reportTime) {
        this.reportDateTime = reportTime;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(LocalDateTime reportTime) {
        this.reportDateTime = reportTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
