package com.teamblue.safetyapp;

//import java.io.IOException;
import java.util.List;

import com.teamblue.safetyapp.Models.Report;

public class IncidentFilter {
    public static void filterIncidents(Report data) {
        // Send to Map Manager if incident is not a non-crime or e-crash
        if (!(data.getReportType ().equals ("Non-Crime") || data.getReportType ().equals ("e-Crash"))) {
            MapManager.sendToDatabase (data);
        }
    }
}
