package com.teamblue.safetyapp.Models;

import java.awt.geom.Point2D;

public class Location {
    private Point2D.Double coordinates;
    private double latitude;
    private double longitude;

    public Location(String latitudeString, String longitudeString) {

        try {
            this.latitude = Double.parseDouble(latitudeString);
            this.longitude = Double.parseDouble(longitudeString);
            this.coordinates = new Point2D.Double(latitude, longitude);
        } catch (NumberFormatException e) {
            // Handle the error here, such as logging or displaying an error message
            throw new NumberFormatException("Error parsing latitude or longitude: " + e.getMessage());
        }
    }

    public Point2D.Double getCoordinates() {
        return coordinates;
    }

    public Double getLatitude() {
        return coordinates.x;
    }

    public Double getLongitude() {
        return coordinates.y;
    }

    public void setCoordinates(Point2D.Double coordinates) {
        this.coordinates = coordinates;
    }
}
