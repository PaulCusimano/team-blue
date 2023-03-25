package com.teamblue.safetyapp;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.writers.CSVWriter;
import technology.tabula.writers.JSONWriter;

public class DataConversion {

    // Main method to run the code
    public static void main(String[] args) throws IOException {

        // Load the PDF file
        File file = new File("team-blue\\safety-app\\src\\main\\resources\\PDcrimelog.pdf");
        InputStream in = new FileInputStream(file);

        // Load the document and extract the tables
        try (PDDocument document = PDDocument.load(in);
             ObjectExtractor oe = new ObjectExtractor(document)) {

            // Use SpreadsheetExtractionAlgorithm to extract tables
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = oe.extract();
            List<Table> tables = new ArrayList<>();

            // Iterate over the pages of the document
            while (pi.hasNext()) {
                Page page = pi.next();
                List<Table> pageTables = sea.extract(page);
                tables.addAll(pageTables);
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
}
