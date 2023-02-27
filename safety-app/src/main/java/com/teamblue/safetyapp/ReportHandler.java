package com.teamblue.safetyapp;

import java.time.LocalDateTime;

public class ReportHandler {
    public void handleReport(Report incidentReport) {
        // do stuff with data.
        System.out.println("New report created:");
        System.out.println("Type: " + incidentReport.getType());
        System.out.println("Name: " + incidentReport.getName());
        System.out.println("Location: " + incidentReport.getLatitude() + ", " + incidentReport.getLongitude());
        System.out.println("Time of Incident: " + incidentReport.getIncidentTime());
        System.out.println("Time of Report: " + incidentReport.getReportTime());
    }

    public static void verification(int i) {

        return;
    }

    public static void curLocation(int location) {

        return;
    }
}
