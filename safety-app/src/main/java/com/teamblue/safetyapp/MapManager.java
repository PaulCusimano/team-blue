package com.teamblue.safetyapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
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
                "C:\\Users\\cobkn\\OneDrive\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json");

        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("campus-safety-294f4")
                .build();

        Firestore db = firestoreOptions.getService();

        Map<String, Object> data = new HashMap<>();
        data.put("latitude", report.getLocation().getLatitude());
        data.put("longitutde", report.getLocation().getLongitude());
        data.put("relatedReport", report.getReportDescription());
        // asynchronously write data
        ApiFuture<DocumentReference> docRef = db.collection("locations_document").add(data);
        // Add document data from report using a hashmap

    }
}
