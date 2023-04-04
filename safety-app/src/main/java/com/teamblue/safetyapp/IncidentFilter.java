package com.teamblue.safetyapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
//import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Date;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.WriteResult;
import com.teamblue.safetyapp.Models.Report;

public class IncidentFilter {
    public static void filterIncidents(Report data) throws IOException, InterruptedException, ExecutionException {
        if (!(data.getReportType().equals("Non-Crime") || data.getReportType().equals("eCrash"))) {
            // MapManager.sendToDatabase(data);

            Firestore db = FirestoreDatabase.getFirestore();

            GeoPoint location = new GeoPoint(data.getLocation().getLatitude(), data.getLocation().getLongitude());

            LocalDateTime localDateTime = data.getReportDateTime();
            Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
            Timestamp timestamp = Timestamp.ofTimeSecondsAndNanos(instant.getEpochSecond(), instant.getNano());

            LocalDateTime incidentDateTime = data.getIncidentDateTime();
            Instant instantIncident = incidentDateTime.toInstant(ZoneOffset.UTC);
            Timestamp incidentTimestamp = Timestamp.ofTimeSecondsAndNanos(instant.getEpochSecond(), instant.getNano());

            DocumentReference docRef = db.collection("reports").document(data.getReportName());

            DocumentReference userRef = db.collection("users").document("Vm9E2TPfIg0QVtyszgYg");

            Map<String, Object> ReportData = new HashMap<>();
            ReportData.put("reportDescription", data.getReportDescription());
            ReportData.put("reportName", "Autogenerated Report From LSUPD");
            ReportData.put("reportType", data.getReportType());
            ReportData.put("status", data.getStatus());
            ReportData.put("location", location);
            ReportData.put("reportDateTime", timestamp);
            ReportData.put("incidentDateTime", incidentTimestamp);
            ReportData.put("reportUser", userRef);
            ReportData.put("reporterEmail", "lsupd@lsu.edu");
            ReportData.put("nearCampus", true);
            ReportData.put("SemanticLocation", data.getSemanticLocation());

            ApiFuture<WriteResult> future = docRef.set(ReportData);
            future.get();

            // Send to Map Manager if incident is not a non-crime or e-crash
            MapManager.sendToDatabase(data);
        }
    }
}
