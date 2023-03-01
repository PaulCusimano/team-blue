package com.teamblue.safetyapp;

import com.teamblue.safetyapp.Models.Report;

public class ReportHandler {

    /**
     * This method handles a report and prints its data to the console.
     *
     * @param incidentReport the report to handle
     */
    public void handleReport(Report incidentReport) {
        // Do stuff with data.
        System.out.println("New report created:");
        System.out.println("Name: " + incidentReport.getReportName());
        System.out.println("Type: " + incidentReport.getReportType());
        System.out.println("Location: " + incidentReport.getLocation().getLatitude() + ", "
                + incidentReport.getLocation().getLongitude());
        System.out.println("Time of Incident: " + incidentReport.getIncidentDateTime());
        System.out.println("Time of Report: " + incidentReport.getReportDateTime());
        System.out.println("Description:" + incidentReport.getReportDescription());
        System.out.println("Status:" + incidentReport.getStatus());
    }

    public static void verification(int i) {

        return;
    }

    public static void curLocation(int location) {

        return;
    }
}
