package com.teamblue.safetyapp;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.v1.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.teamblue.safetyapp.Models.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.teamblue.safetyapp.Models.Location;
import com.teamblue.safetyapp.Models.Report;

public class LocationValidation {

    private double latitude;
    private double longitude;
    private String reference;
    private boolean validatedLocation;

    public LocationValidation(double latitude, double longitude, String reference) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.reference = reference;
    }

    public boolean validateLocation() {
        validatedLocation = (latitude > 30.394458 && latitude < 30.421867400521673 && longitude > -91.200675
                && longitude < -91.165824);
        return validatedLocation;
    }

    public void saveNearCampusToFirestore(boolean nearCampus)
            throws IOException, InterruptedException, ExecutionException {
        FileInputStream serviceAccount = new FileInputStream(
                "C:\\Users\\cobkn\\OneDrive\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json");
        // C:\\Users\\hagri\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("campus-safety-294f4")
                .build();
        Firestore db = firestoreOptions.getService();

        DocumentReference docRef = db.collection("reports").document(reference);

        Map<String, Object> data = new HashMap<>();
        data.put("nearCampus", nearCampus);

        ApiFuture<WriteResult> future = docRef.update(data);
        future.get();

        System.out.println("nearCampus: " + validatedLocation);
    }


    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        Report report = new Report();
        double longitude = report.getLocation().getLongitude();
        double latitude = report.getLocation().getLatitude();
        String reference = report.getReference();

        LocationValidation locationValidation = new LocationValidation(latitude, longitude, reference);
        boolean nearCampus = locationValidation.validateLocation();
        locationValidation.saveNearCampusToFirestore(nearCampus);
    }
}
