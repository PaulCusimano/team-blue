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
import java.io.IOException;

public class chatHandler {
    private static String sender =  "bob";
    private static String reciever = "George";
    private static String message = "Hello";


    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

        //Message userMessage = new Message(sender, message, reciever);
        

		FileInputStream serviceAccount = new FileInputStream(
				"C:\\Users\\cobkn\\OneDrive\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json");

		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setProjectId("campus-safety-294f4")
				.build();
		Firestore db = firestoreOptions.getService();

		DocumentReference docRef = db.collection("chats").document("doc.id");

		Map<String, Object> data = new HashMap<>();
		data.put("user", reciever);
		data.put("police", sender);
		data.put("message", message);
    }
}
