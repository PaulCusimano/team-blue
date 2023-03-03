package com.teamblue.safetyapp.Models;

import java.awt.geom.Point2D;

public class Location {
    // Store location as a Point2D object for easy access to x and y coordinates
    private Point2D.Double coordinates;
    // Store latitude and longitude as separate variables for easy initialization
    private float latitude;
    private float longitude;

    // Constructor takes in latitude and longitude as strings
    public Location(Float latitudeFloat, Float longitudeFloat) {
        try {

            // Parse latitude and longitude strings as doubles
            this.latitude = latitudeFloat;
            this.longitude = longitudeFloat;
            // Store the coordinates as a Point2D object
            this.coordinates = new Point2D.Double(latitude, longitude);
        } catch (NumberFormatException e) {
            // Throw an exception if the latitude or longitude cannot be parsed as a double
            throw new NumberFormatException("Error parsing latitude or longitude: " + e.getMessage());
        }
    }

    // Getter method for coordinates
    public Point2D.Double getCoordinates() {
        return coordinates;
    }

    // Getter method for latitude
    public Double getLatitude() {
        return coordinates.x;
    }

    // Getter method for longitude
    public Double getLongitude() {
        return coordinates.y;
    }

    // Setter method for coordinates
    public void setCoordinates(Point2D.Double coordinates) {
        this.coordinates = coordinates;
    }
}
