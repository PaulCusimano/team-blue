package com.teamblue.safetyapp;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.teamblue.safetyapp.Models.Report;

public class ReportHandler {

    /**
     * This method handles a report and prints its data to the console.
     *
     * @param incidentReport the report to handle
     */
    public void handleReport(Report incidentReport) {
        // Prints the report to the console.
        System.out.println("New report created:");
        System.out.println("Name: " + incidentReport.getReportName());
        System.out.println("Type: " + incidentReport.getReportType());
        System.out.println("Location: " + incidentReport.getLocation().getLatitude() + ", "
                + incidentReport.getLocation().getLongitude());
        System.out.println("Time of Incident: " + incidentReport.getIncidentDateTime());
        System.out.println("Time of Report: " + incidentReport.getReportDateTime());
        System.out.println("Description:" + incidentReport.getReportDescription());
        System.out.println("Status:" + incidentReport.getStatus());

        // Sends the report to the IncidentFilter class to be filtered
        try {
            IncidentFilter.filterIncidents(incidentReport);
        } catch (IOException | InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void filtering(Report Report) throws IOException, InterruptedException, ExecutionException {
        IncidentFilter filteringIncident = new IncidentFilter(); // calls incident filter to filter out the user report
        IncidentFilter.filterIncidents(Report);
    }

    public static void verification(int i) {

        return;
    }

    public static void curLocation(int location) {

        return;
    }
}
