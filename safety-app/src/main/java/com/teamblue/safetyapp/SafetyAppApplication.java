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
public class SafetyAppApplication {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		// You must download the key file (in the discord) and save the path to it in
		// here everytime you run the code
		FileInputStream serviceAccount = new FileInputStream(
				"C:\\Users\\cobkn\\OneDrive\\Desktop\\campus-safety-294f4-firebase-adminsdk-lc5oa-5c74129f44.json");

		// FirebaseOptions options = FirebaseOptions.builder()
		// .setCredentials(GoogleCredentials.fromStream(serviceAccount))
		// .setProjectId("campus-safety-294f4")
		// .build();

		// FirebaseApp.initializeApp(options);

		// Firestore firestore = FirestoreClient.getFirestore();
		// DocumentSnapshot snapshot =
		// firestore.collection("users").document("alice").get().get();

		FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setProjectId("campus-safety-294f4")
				.build();
		Firestore db = firestoreOptions.getService();

		DocumentReference docRef = db.collection("users").document("aturing");
		// Add document data with id "alovelace" using a hashmap
		Map<String, Object> data = new HashMap<>();
		data.put("first", "Alan");
		data.put("last", "Turing");
		data.put("born", 1912);
		// asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
		// ...
		// result.get() blocks on response
		System.out.println("Update time : " + result.get().getUpdateTime());

		// Add document data with auto-generated id.
		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("users").get();
		// ...
		// query.get() blocks on response
		String firstname = "null";
		String lastname = "null";
		Long born = null;

		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			System.out.println("User: " + document.getId());
			System.out.println("First: " + document.getString("first"));
			if (document.contains("middle")) {
				System.out.println("Middle: " + document.getString("middle"));
			}
			firstname = document.getString("first");
			lastname = document.getString("last");
			born = document.getLong("born");
			System.out.println("Last: " + document.getString("last"));
			System.out.println("Born: " + document.getLong("born"));
		}

		DocumentReference docuRef = db.collection("users").document("77FoxF6XLoYAOlGE06zGr94SZ7g2");
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future = docuRef.get();
		// ...
		// future.get() blocks on response
		DocumentSnapshot document = future.get();
		if (document.exists()) {
			System.out.println("Document data: " + document.getData());
		} else {
			System.out.println("No such document!");
		}

		SpringApplication.run(SafetyAppApplication.class, args);
		System.out.println(firstname + " " + lastname + " " + born);
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
			@RequestParam String incidentDescription) {

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
				status);

		// Send the report to the ReportHandler to be processed.
		ReportHandler reportHandler = new ReportHandler();
		reportHandler.handleReport(report);

		// Return a message indicating that the report has been successfully created.
		return String.format("Report created successfully.");
	}

	@PostMapping("/sendMessage")
	public String sendMessage(@RequestParam String senderName, @RequestParam String message, @RequestParam String recieverName) {
		Message userMessage = new Message(senderName, message, recieverName);
		return String.format("Message recieved");
	}

	// VVV EXAMPLE METHODS OF HOW TO USE RESTAPIs VVV

	/**
	 * 
	 * GET request for returning a greeting with a name.
	 * 
	 * @param name The name to include in the greeting. Defaults to "World" if not
	 *             provided.
	 * @return A greeting with the provided name.
	 */
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	/**
	 * 
	 * GET request for multiplying a number by 5.
	 * 
	 * @param number The number to multiply. Defaults to 5 if not provided.
	 * @return The provided number multiplied by 5.
	 */
	@GetMapping("/multiply")
	public String multiply(@RequestParam(value = "number", defaultValue = "5") int number) {
		number = number * 5;
		return String.format("Here is your value multiplied by 5: %s", number);
	}

	/**
	 * 
	 * GET request for adding two numbers together.
	 * 
	 * @param num1 The first number to add.
	 * 
	 * @param num2 The second number to add.
	 * 
	 * @return The sum of the two provided numbers.
	 */
	@RequestMapping(value = "/sum", method = RequestMethod.GET)
	public ResponseEntity<Integer> getSum(@RequestParam("num1") int num1, @RequestParam("num2") int num2) {

		int sum = num1 + num2;
		return new ResponseEntity<Integer>(sum, HttpStatus.OK);
	}

	/**
	 * 
	 * GET request for adding a list of numbers together.
	 * 
	 * @param numbers A list of numbers to add together, separated by commas.
	 * @return The sum of the provided numbers.
	 */
	@RequestMapping(value = "/ArrSum", method = RequestMethod.GET)
	public ResponseEntity<Integer> getArraySum(@RequestParam(value = "numbers") List<Integer> numbers) {
		int sum = 0;
		for (int number : numbers) {
			sum += number;
		}
		return new ResponseEntity<Integer>(sum, HttpStatus.OK);
	}
	// END OF EXAMPLE METHODS
}
