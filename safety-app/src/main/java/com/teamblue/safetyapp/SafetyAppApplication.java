package com.teamblue.safetyapp;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
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

	// Test using POSTMAN. Post mappings require a request body, which cant easily
	// be sent via browser input.
	// Time must be formatted as yyyy-MM-dd HH:mm in the POST request.
	@PostMapping("/MakeReport")
	public String createReport(@RequestParam String type, @RequestParam String name, @RequestParam String latitude,
			@RequestParam String longitude, @RequestParam String incidentTime) {

		// This converts the incidentTime string into a java LocalDateTime object.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime incidentTimeFormatted = LocalDateTime.parse(incidentTime, formatter);

		// Time of the report
		LocalDateTime reportTime = LocalDateTime.now();
		Location userLocation = new Location(latitude, longitude);

		Report report = new Report(type, name, userLocation, incidentTimeFormatted, reportTime);

		ReportHandler reportHandler = new ReportHandler();
		reportHandler.handleReport(report);
		return String.format("Made Report");
	}

	// EXAMPLE METHODS.

	// test at: http://localhost:8080/hello?name=<INSERT NAME HERE>
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	// test at: http://localhost:8080/multiply?number=5
	@GetMapping("/multiply")
	public String addition(@RequestParam(value = "number", defaultValue = "5") int number) {
		number = number * 5;
		return String.format("Here is your value multiplied by 5: %s", number);
	}

	// test at: http://localhost:8080/sum?num1=<FIRST NUMBER>&num2=<SECOND NUMBER>
	@RequestMapping(value = "/sum", method = RequestMethod.GET)
	public ResponseEntity<Integer> getSum(@RequestParam("num1") int num1, @RequestParam("num2") int num2) {

		int sum = num1 + num2;
		return new ResponseEntity<Integer>(sum, HttpStatus.OK);
	}

	// http://localhost:8080/ArrSum?numbers=<NUMBER1>,<NUMBER2>,<NUMBER3>,......
	@RequestMapping(value = "/ArrSum", method = RequestMethod.GET)
	public ResponseEntity<Integer> getSum(@RequestParam(value = "numbers") List<Integer> numbers) {
		int sum = 0;
		for (int number : numbers) {
			sum += number;
		}
		return new ResponseEntity<Integer>(sum, HttpStatus.OK);
	}
	// END EXAMPLE METHODS.

}
