package com.teamblue.safetyapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.GeoPoint;
import com.google.cloud.firestore.WriteResult;
import com.teamblue.safetyapp.Models.Report;

public class FirestoreDatabase {
    // Declare the constants for the service account path and project ID
    private static final String SERVICE_ACCOUNT_PATH = "C:\\Users\\hagri\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json";
    private static final String PROJECT_ID = "campus-safety-294f4";

    // Declare a private static field for the Firestore object
    private static Firestore firestore;

    // Declare a private constructor to prevent instantiation
    private FirestoreDatabase() {
    }

    // Declare a public static method to get the Firestore object
    public static Firestore getFirestore() throws IOException {
        // Check if the firestore field is null
        if (firestore == null) {
            synchronized (FirestoreDatabase.class) {
            // Read the service account JSON file as an input stream
            FileInputStream serviceAccount = new FileInputStream(SERVICE_ACCOUNT_PATH);
            // Build a FirestoreOptions object with the credentials and project ID
            FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId(PROJECT_ID)
                    .build();
            // Assign the firestore field to the service provided by the options
            firestore = firestoreOptions.getService();
            }
        }
        // Return the firestore field
        return firestore;
    }
}