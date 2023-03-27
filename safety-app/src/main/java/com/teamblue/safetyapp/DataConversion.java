package com.teamblue.safetyapp;

import java.io.IOException;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.writers.CSVWriter;
import technology.tabula.writers.JSONWriter;

public class DataConversion {

    // Convert the first two pages of PDF and write to a CSV + JSON file
    public static void convertPDF() throws IOException {

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
}