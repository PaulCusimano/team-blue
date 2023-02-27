package com.teamblue.safetyapp;

import java.time.LocalDateTime;

public class Report {

    private String type;
    private String name;
    private String latitude;
    private String longitude;
    private LocalDateTime incidentTime;
    private LocalDateTime reportTime;

    public Report(String type, String name, String latitude, String longitude, LocalDateTime incidentTime,
            LocalDateTime reportTime) {
        this.type = type;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.incidentTime = incidentTime;
        this.reportTime = reportTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getIncidentTime() {
        return incidentTime;
    }

    public void setIncidentTime(LocalDateTime incidentTime) {
        this.incidentTime = incidentTime;
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public void setReportTime(LocalDateTime reportTime) {
        this.reportTime = reportTime;
    }

}
