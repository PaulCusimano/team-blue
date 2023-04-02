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

public class MapManager {
    // apon importing location data from the firebase database, it will then send
    // that location to the google maps api every 24 hours.
    // This class doesnt actually communicate with any of our other code directly
    public static void sendToDatabase(Report report) throws IOException, InterruptedException, ExecutionException {
        // TODO Auto-generated method stub
        // put the report in location DB

        FileInputStream serviceAccount = new FileInputStream(
                "C:\\Users\\hagri\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json");
        // C:\\Users\\hagri\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("campus-safety-294f4")
                .build();
        Firestore db = firestoreOptions.getService();

        GeoPoint location = new GeoPoint(report.getLocation().getLatitude(), report.getLocation().getLongitude());

        // asynchronously write data
        // DocumentReference docRef =
        // db.collection("locations_document").document(report.getReportName());

        // DocumentReference reportRef =
        // db.collection("reports").document(report.getReportName());

        // Map<String, Object> data = new HashMap<>();
        // data.put("IncidentLocations", location);
        // data.put("IncidentDescription", report);
        // data.put("relatedReport", reportRef);

        // docRef.set(data);
        DocumentReference docRef = db.collection("locations_document").document(report.getReportName());

        DocumentReference reportRef = db.collection("reports").document(report.getReportName());

        Map<String, Object> ReportData = new HashMap<>();
        ReportData.put("IncidentLocations", location);
        ReportData.put("IncidentDescription", report.getReportDescription());
        ReportData.put("relatedReport", reportRef);
        ReportData.put("SemanticLocation", report.getSemanticLocation());

        ApiFuture<WriteResult> future = docRef.set(ReportData);
        future.get();

    }
}
