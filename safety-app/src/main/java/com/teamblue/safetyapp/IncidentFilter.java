package com.teamblue.safetyapp;

import java.io.IOException;
import java.util.List;

public class IncidentFilter {
    public static void filterIncidentsFromPDF () throws IOException {
        List<String> incidents = DataConversion.pdfToString ();
    }
}
