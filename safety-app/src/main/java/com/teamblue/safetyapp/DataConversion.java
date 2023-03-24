package com.teamblue.safetyapp;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import technology.tabula.*;

//Not tested, but theoretically currently only outputs to console. Need to add CSV and JSON as outputs
@SuppressWarnings("rawtypes")
public class DataConversion {
    public static void pdfConversion() throws IOException {
        File file = new File("safety-app/src/main/resources/PDcrimelog.pdf");
        InputStream in = new FileInputStream(file);
        try (PDDocument document = PDDocument.load(in);
                ObjectExtractor oe = new ObjectExtractor(document)) {
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            PageIterator pi = oe.extract();
            while (pi.hasNext()) {
                // iterate over the pages of the document
                Page page = pi.next();
                List<Table> table = sea.extract(page);
                // iterate over the tables of the page
                for (Table tables : table) {
                    List<List<RectangularTextContainer>> rows = tables.getRows();
                    // iterate over the rows of the table
                    for (List<RectangularTextContainer> cells : rows) {
                        // print all column-cells of the row plus linefeed
                        for (RectangularTextContainer content : cells) {
                            // Note: Cell.getText() uses \r to concat text chunks
                            String text = content.getText().replace("\r", " ");
                            System.out.print(text + "|");
                        }
                        System.out.println();
                    }
                }
            }
        }
    }
}
