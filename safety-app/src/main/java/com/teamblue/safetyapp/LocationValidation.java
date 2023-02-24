package com.teamblue.safetyapp;

import java.io.*;
public class LocationValidation {

    public static void getLocation() throws IOException {
        int location = 0;
        ReportHandler.curLocation(location);
    }

}