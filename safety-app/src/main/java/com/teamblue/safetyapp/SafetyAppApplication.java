package com.teamblue.safetyapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SafetyAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SafetyAppApplication.class, args);
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
