package com.example.crowderapp.models;

/**
 * Represents a location for a trial.
 */
public class Location {
    public double longitude;
    public double latitude;

    public Location() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
