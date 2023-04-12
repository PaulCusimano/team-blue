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

        Firestore db = FirestoreDatabase.getFirestore();

        DocumentReference docRef = db.collection("reports").document(reference);

        Map<String, Object> data = new HashMap<>();
        data.put("nearCampus", nearCampus);

        WriteResult writeResult = docRef.update(data).get();
        System.out.println("nearCampus: " + nearCampus);
    }

}
