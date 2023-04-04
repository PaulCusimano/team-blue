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

import com.teamblue.safetyapp.Models.Location;
import com.teamblue.safetyapp.Models.Message;
import com.teamblue.safetyapp.Models.Report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@RestController
public class APIManager {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		// You must download the key file (in the discord) and save the path to it in
		// here everytime you run the code
		FileInputStream serviceAccount = new FileInputStream(
				"C:\\Users\\hagri\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json");

		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setProjectId("campus-safety-294f4")
				.build();
		Firestore db = firestoreOptions.getService();

		SpringApplication.run(APIManager.class, args);
	}

	/**
	 * 
	 * POST request for creating a report. It can be easily tested by
	 * using Postman.
	 * 
	 * It requires a request body which cannot easily be sent through browser input.
	 * 
	 * @param type             The type of the incident reported.
	 * 
	 * @param name             The name of the person reporting the incident.
	 * 
	 * @param latitude         The latitude of the location where the incident
	 *                         occurred.
	 * 
	 * @param longitude        The longitude of the location where the incident
	 *                         occurred.
	 * 
	 * @param incidentDateTime The date and time when the incident occurred. Must be
	 *                         in
	 *                         yyyy-MM-dd HH:mm format.
	 * 
	 * @param description      The description of the incident.
	 * 
	 * @return A message indicating that the report has been successfully created.
	 */
	@PostMapping("/MakeReport")
	public String createReport(@RequestParam String type, @RequestParam String name, @RequestParam String latitude,
			@RequestParam String longitude, @RequestParam String incidentDateTime,
			@RequestParam String incidentDescription, @RequestParam String referenceReport) {

		System.out.println("Creating report...");
		System.out.println(referenceReport);
		String[] parts = longitude.substring(7, longitude.length() - 1).split(", ");

		String latitudeString = parts[0].substring(4);
		String longitudeString = parts[1].substring(4);

		Float latitudeFloat = Float.parseFloat(latitudeString);
		Float longitudeFloat = Float.parseFloat(longitudeString);

		// Convert the incidentTime string into a LocalDateTime object.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime incidentTimeFormatted = LocalDateTime.parse(incidentDateTime, formatter);

		// Get the current time as the report time.
		LocalDateTime reportDateTime = LocalDateTime.now();

		// Create a Location object using the latitude and longitude provided.
		Location userLocation = new Location(latitudeFloat, longitudeFloat);

		// New reports are open by default. Can be marked cleared upon resolution.
		String status = "OPEN";

		// Create a Report object with the information provided.
		Report report = new Report(type, name, userLocation, incidentTimeFormatted, reportDateTime, incidentDescription,
				status, referenceReport);

		// Send the report to the ReportHandler to be processed.
		ReportHandler reportHandler = new ReportHandler();
		reportHandler.handleReport(report);

		// Return a message indicating that the report has been successfully created.
		return String.format("Report created successfully.");
	}

	@PostMapping("/sendMessage")
	public String sendMessage(@RequestParam String senderName, @RequestParam String message,
			@RequestParam String recieverName) {
		Message userMessage = new Message(senderName, message, recieverName);
		return String.format("Message recieved");
	}
	// END OF EXAMPLE METHODS
}
