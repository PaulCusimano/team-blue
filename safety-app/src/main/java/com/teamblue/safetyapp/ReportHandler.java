package com.teamblue.safetyapp;

import java.time.LocalDateTime;

public class ReportHandler {
    public void handleReport(String type, String name, String latitude, String longitude, LocalDateTime incidentTime,
            LocalDateTime reportTime) {
        // do stuff with data.
        System.out.println("New report created:");
        System.out.println("Type: " + type);
        System.out.println("Name: " + name);
        System.out.println("Location: " + latitude + ", " + longitude);
        System.out.println("Time of Incident: " + incidentTime);
        System.out.println("Time of Report: " + reportTime);
    }

    public static void verification(int i) {

        return;
    }

    public static void curLocation(int location) {

        return;
    }
}
