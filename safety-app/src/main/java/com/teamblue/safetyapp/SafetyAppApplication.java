package com.teamblue.safetyapp;

// import com.google.auth.oauth2.GoogleCredentials;
// import com.google.firebase.FirebaseApp;
// import com.google.firebase.FirebaseOptions;
// import com.google.firebase.database.*;
import com.teamblue.safetyapp.Models.Location;
import com.teamblue.safetyapp.Models.Report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@RestController
public class SafetyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyAppApplication.class, args);
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

		String inputString = "LatLng(lat: 0, lng: 0)";
		String[] parts = longitude.substring(7, longitude.length() - 1).split(", ");

		latitude = parts[0].substring(4);
		longitude = parts[1].substring(4);

		// Convert the incidentTime string into a LocalDateTime object.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
		LocalDateTime incidentTimeFormatted = LocalDateTime.parse(incidentDateTime, formatter);

		// Get the current time as the report time.
		LocalDateTime reportDateTime = LocalDateTime.now();

		// Create a Location object using the latitude and longitude provided.
		Location userLocation = new Location(latitude, longitude);

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
