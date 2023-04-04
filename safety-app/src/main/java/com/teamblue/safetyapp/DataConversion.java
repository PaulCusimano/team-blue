package com.teamblue.safetyapp;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.cglib.core.Local;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.teamblue.safetyapp.Models.Location;
import com.teamblue.safetyapp.Models.Report;

import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.writers.CSVWriter;
import technology.tabula.writers.JSONWriter;

public class DataConversion {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        String pdfUrl = "https://www.lsu.edu/police/files/crime-log/dcfr.pdf";
        String jsonFilePath = "safety-app\\src\\main\\resources\\JSONoutput.json";
        convertPDFToCSV(pdfUrl, jsonFilePath);

        List<Report> reports = readJSON(jsonFilePath);

        System.out.println("Beginning report upload...");

        for (Report report : reports) {
            Thread.sleep(50);

            System.out
                    .println(report.getReportType() + " " + report.getReportName() + " "
                            + report.getLocation().getLatitude() + " " +
                            report.getLocation().getLatitude() + " " +
                            report.getIncidentDateTime() + " " + report.getReportDateTime() + " "
                            + report.getReportDescription() + " " +
                            report.getStatus() + " " + report.getReference());

            if (!(report.getReportName() == null || report.getReportType() == null
                    || report.getLocation() == null
                    || report.getIncidentDateTime() == null || report.getReportDateTime() == null)) {
                System.out.println("Valid Report");
                IncidentFilter.filterIncidents(report);
            }
            System.out.println("------------------------");

        }

    }

    // Convert the first two pages of PDF and write to a CSV + JSON file
    public static void convertPDFToCSV(String pdfUrl, String csvFilePath) throws IOException {

        // Load the PDF file from the URL
        URL url = new URL("https://www.lsu.edu/police/files/crime-log/dcfr.pdf");
        InputStream in = url.openStream();

        // Load the document and extract the tables
        try (PDDocument document = PDDocument.load(in);
                ObjectExtractor oe = new ObjectExtractor(document)) {

            // Use SpreadsheetExtractionAlgorithm to extract tables
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = oe.extract();
            List<Table> tables = new ArrayList<>();

            // Iterate over the first two pages of the PDF file
            int pageCount = 0;
            while (pi.hasNext() && pageCount < 2) {
                Page page = pi.next();
                List<Table> pageTables = sea.extract(page);
                tables.addAll(pageTables);
                pageCount++;
            }

            // Convert the tables to JSON format and write to file
            JSONWriter jsonWriter = new JSONWriter();
            FileWriter jsonOut = new FileWriter("team-blue\\safety-app\\src\\main\\resources\\JSONoutput.json");
            jsonWriter.write(jsonOut, tables);

            // Convert the tables to CSV format and write to file
            CSVWriter csvWriter = new CSVWriter();
            FileWriter csvOut = new FileWriter("team-blue\\safety-app\\src\\main\\resources\\CSVoutput.csv");
            csvWriter.write(csvOut, tables);

        } finally {
            in.close();
        }
    }

    // public static List<Report> readCSV(String csvFilePath) throws IOException {
    // List<Report> reports = new ArrayList<>();

    // try (Reader reader = new FileReader(csvFilePath);
    // CSVReader csvReader = new CSVReaderBuilder(reader)
    // .withSkipLines(1)
    // .withCSVParser(new CSVParserBuilder().withSeparator(',').build())
    // .withVerifyReader(false)
    // .build()) {
    // String[] line;
    // while ((line = csvReader.readNext()) != null) {
    // System.out.println(Arrays.toString(line));
    // String reportType = line[4];
    // String reportName = "LSUPD Report";
    // Location location = convertToCoordinates(line[7]);
    // LocalDateTime incidentDate = convertStringToLocalDateTime(line[3]);
    // LocalDateTime reportDate = convertStringToLocalDateTime(line[1]);
    // String reportDescription = line[6];
    // String status = line[8];
    // String reference = line[2];

    // Report report = new Report(reportType, reportName, location, incidentDate,
    // reportDate,
    // reportDescription,
    // status, reference);
    // reports.add(report);
    // }
    // }
    // return reports;
    // }

    public static List<Report> readJSON(String jsonFilePath) throws IOException {

        List<Report> reports = new ArrayList<>();
        String jsonString = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        JSONArray jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            System.out.println("i:" + i);
            Report report = new Report();
            for (int j = 0; j < dataArray.length(); j++) {
                System.out.println("j:" + j);
                JSONArray innerArray = dataArray.getJSONArray(j);
                for (int k = 0; k < innerArray.length(); k++) {
                    JSONObject dataObject = innerArray.getJSONObject(k);
                    String text = dataObject.getString("text");
                    System.out.println("k:" + k);
                    System.out.println(text);
                    if (k == 1) {

                    } else if (k == 2) {

                    } else if (k == 3) {

                    }
                    switch (k) {
                        case 0:
                            report.setReportDateTime(convertStringToLocalDateTime(text));
                            break;
                        case 1:
                            report.setReportName(text);
                            report.setReference("/reports/" + text);
                            break;
                        case 2:
                            report.setIncidentDateTime(convertStringToLocalDateTime(text));
                            break;
                        case 3:
                            report.setReportType(text);
                            break;
                        case 5:
                            report.setReportDescription(text);
                            break;
                        case 6:
                            if (text.equals("Cleared")) {
                                report.setStatus("CLEARED");
                            } else if (text.equals("Closed")) {
                                report.setStatus("CLOSED");
                            } else if (text.equals("Open")) {
                                report.setStatus("OPEN");
                            } else {
                                report.setStatus("UNKNOWN");
                            }
                            break;
                        case 7:
                            report.setSemanticLocation(text);
                            report.setLocation(convertToCoordinates(text));
                            System.out.println(report.getLocation());
                            break;
                    }
                }
            }
            reports.add(report);
        }
        return reports;
    }

    public static Location convertToCoordinates(String semanticLocation) throws IOException {

        // Create a URL for the Google Maps Geocoding API request
        System.out.println(URLEncoder.encode(semanticLocation, "UTF-8"));
        String requestURL = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + URLEncoder.encode(semanticLocation, "UTF-8")
                + ",+Baton+Rouge,+LA&key=AIzaSyCoqncEOlFW6r-JsTC6aj1HqowYVQyLEAY    ";

        // 3279+Dalrymple+Dr,+Baton+Rouge,+LA
        // Send the request to the API and retrieve the response
        URL url = new URL(requestURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = responseReader.readLine()) != null) {
            responseBuilder.append(line);
        }
        responseReader.close();
        String response = responseBuilder.toString();

        // Parse the JSON response to retrieve the latitude and longitude
        JSONObject responseJSON = new JSONObject(response);
        if (!responseJSON.getString("status").equals("OK")) {
            // Handle any errors returned by the API
            throw new IOException("Error retrieving coordinates for location " + semanticLocation);
        }
        JSONObject locationJSON = responseJSON.getJSONArray("results")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONObject("location");
        float latitude = (float) locationJSON.getDouble("lat");
        float longitude = (float) locationJSON.getDouble("lng");

        // Create a new Location object with the retrieved coordinates
        return new Location(latitude, longitude);
    }

    public static LocalDateTime convertStringToLocalDateTime(String inputDateTime) {
        if (inputDateTime == null || inputDateTime.isEmpty()) {
            // Handle empty input string here, either by returning null or throwing an
            // exception
            return null;
        }

        LocalDateTime localDateTime = null;

        // Define the format of the output date string
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            // Define the format of the input date string with time information
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");

            // Parse the input date string into a LocalDateTime object using the input
            // formatter
            localDateTime = LocalDateTime.parse(inputDateTime, inputFormatter);
        } catch (DateTimeParseException e) {
            // The input date string does not contain time information
            // Define the format of the input date string without time information
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

            // Parse the input date string into a LocalDateTime object using the input
            // formatter
            localDateTime = LocalDate.parse(inputDateTime, inputFormatter).atStartOfDay();
        }

        // Format the LocalDateTime object into the desired output format
        String formattedDateTime = localDateTime.format(outputFormatter);

        // Parse the formatted date string back into a LocalDateTime object
        LocalDateTime formattedLocalDateTime = LocalDateTime.parse(formattedDateTime, outputFormatter);

        return formattedLocalDateTime;
    }

}