package com.teamblue.safetyapp.Models;

import java.time.LocalDateTime;

public class Report {

    private String type;
    private String name;
    private Location location;
    private LocalDateTime incidentTime;
    private LocalDateTime reportTime;

    public Report(String type, String name, Location location, LocalDateTime incidentTime,
            LocalDateTime reportTime) {
        this.type = type;
        this.name = name;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
