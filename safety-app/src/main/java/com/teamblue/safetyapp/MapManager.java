package com.teamblue.safetyapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.WriteResult;
import com.teamblue.safetyapp.Models.Report;

// This class manages the communication with the Firestore database
public class MapManager {
    // The name of the collection that stores the locations of the reports
    private static final String LOCATIONS_COLLECTION = "locations_document";
    // The name of the collection that stores the details of the reports
    private static final String REPORTS_COLLECTION = "reports";

    // This method sends a report object to the Firestore database
    public static void sendToDatabase(Report report) throws IOException, InterruptedException, ExecutionException {
        // Get a reference to the Firestore database
        Firestore db = FirestoreDatabase.getFirestore();

        // Create a GeoPoint object from the report's location
        GeoPoint location = new GeoPoint(report.getLocation().getLatitude(), report.getLocation().getLongitude());
        // Get a reference to the document that corresponds to the report's name in the
        // locations collection
        DocumentReference docRef = db.collection(LOCATIONS_COLLECTION).document(report.getReportName());
        // Get a reference to the document that corresponds to the report's name in the
        // reports collection
        DocumentReference reportRef = db.collection(REPORTS_COLLECTION).document(report.getReportName());
        // Create a map object that stores the report's data as key-value pairs
        Map<String, Object> reportData = new HashMap<>();
        reportData.put("IncidentLocations", location);
        reportData.put("IncidentDescription", report.getReportDescription());
        reportData.put("relatedReport", reportRef);
        reportData.put("SemanticLocation", report.getSemanticLocation());
        // Write the report data to the document in the locations collection
        ApiFuture<WriteResult> future = docRef.set(reportData);
        // Wait for the write operation to complete
        future.get();
    }
}