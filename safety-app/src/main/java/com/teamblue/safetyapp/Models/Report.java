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
    private String reference;
    private String semanticLocation;

    // Constructor
    private Report(Builder builder) {
        this.reportType = builder.reportType;
        this.reportName = builder.reportName;
        this.location = builder.location;
        this.incidentDateTime = builder.incidentDateTime;
        this.reportDateTime = builder.reportDateTime;
        this.reportDescription = builder.reportDescription;
        this.status = builder.status;
        this.reference = builder.reference;
        this.semanticLocation = builder.semanticLocation;
    }

    public String getReportType() {
        return reportType;
    }

    public String getReportName() {
        return reportName;
    }

    public Location getLocation() {
        return location;
    }

    public LocalDateTime getIncidentDateTime() {
        return incidentDateTime;
    }

    public LocalDateTime getReportDateTime() {
        return reportDateTime;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public String getStatus() {
        return status;
    }

    public String getReference() {
        return reference;
    }

    public String getSemanticLocation() {
        return semanticLocation;
    }

    public static class Builder {
        // Instance variables
        private String reportType;
        private String reportName;
        private Location location;
        private LocalDateTime incidentDateTime;
        private LocalDateTime reportDateTime;
        private String reportDescription;
        private String status;
        private String reference;
        private String semanticLocation;

        public Builder(String name) {
            this.reportName = name;
        }

        public Builder reportType(String type) {
            this.reportType = type;
            return this;
        }

        public Builder reportName(String name) {
            this.reportName = name;
            return this;
        }

        public Builder location(Location location) {
            this.location = location;
            return this;
        }

        public Builder incidentDateTime(LocalDateTime incidentTime) {
            this.incidentDateTime = incidentTime;
            return this;
        }

        public Builder reportDateTime(LocalDateTime reportTime) {
            this.reportDateTime = reportTime;
            return this;
        }

        public Builder reportDescription(String description) {
            this.reportDescription = description;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder reference(String reference) {
            this.reference = reference;
            return this;
        }

        public Builder semanticLocation(String semanticLocation) {
            this.semanticLocation = semanticLocation;
            return this;
        }

        public Report build() {
            return new Report(this);
        }
    }
}
