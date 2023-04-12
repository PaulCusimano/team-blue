package com.teamblue.safetyapp;

import com.teamblue.safetyapp.Models.Location;
import com.teamblue.safetyapp.Models.Message;
import com.teamblue.safetyapp.Models.Report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@RestController
public class APIManager {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

		// Start the Spring Boot application, which waits for any reports or messages.
		SpringApplication.run(APIManager.class, args);

		// Whenever program is started, it will run the DataConversion and load all the
		// latest reports into the database.
		DataConversion.main(args);
	}

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

		Report report = new Report.Builder(name)
				.reportType(type)
				.location(userLocation)
				.incidentDateTime(incidentTimeFormatted)
				.reportDateTime(reportDateTime)
				.reportDescription(incidentDescription)
				.status(status)
				.reference(referenceReport)
				.build();

		// Send the report to the ReportHandler to be processed.
		ReportHandler reportHandler = new ReportHandler();
		reportHandler.handleReport(report);

		// Return a message indicating that the report has been successfully created.
		return String.format("Report created successfully.");
	}

	@PostMapping("/sendMessage")
	public String sendMessage(@RequestParam String senderName, @RequestParam String message,
			@RequestParam String receiverName) {
		Message userMessage = new Message(senderName, message, receiverName);
		return String.format("Message received");
	}

	@GetMapping("/receiveMessage")
	public String receiveMessage(@RequestParam String receiverName) {
		chatHandler chatHandler = new chatHandler();
		return "temp";
	}

}
