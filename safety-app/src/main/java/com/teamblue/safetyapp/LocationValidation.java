package com.teamblue.safetyapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import com.teamblue.safetyapp.Models.Report;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LocationValidation {

    private final double latitude;
    private final double longitude;
    private final String reference;

    public LocationValidation(double latitude, double longitude, String reference) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.reference = reference;
    }

    public boolean isLocationValid() {
        return (latitude > 30.394458 && latitude < 30.421867400521673 && longitude > -91.200675
                && longitude < -91.165824);
    }

    public void saveNearCampusToFirestore(boolean nearCampus)
            throws IOException, InterruptedException, ExecutionException {
        FileInputStream serviceAccount = new FileInputStream("C:\\Users\\cobkn\\OneDrive\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json");

        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("campus-safety-294f4")
                .build();
        Firestore db = firestoreOptions.getService();

        DocumentReference docRef = db.collection("reports").document(reference);

        Map<String, Object> data = new HashMap<>();
        data.put("nearCampus", nearCampus);

        WriteResult writeResult = docRef.update(data).get();
        System.out.println("nearCampus: " + nearCampus);
    }

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Report report = new Report();
        double longitude = report.getLocation().getLongitude();
        double latitude = report.getLocation().getLatitude();
        String reference = report.getReference();

        LocationValidation locationValidation = new LocationValidation(latitude, longitude, reference);
        boolean nearCampus = locationValidation.isLocationValid();
        locationValidation.saveNearCampusToFirestore(nearCampus);
    }
}
