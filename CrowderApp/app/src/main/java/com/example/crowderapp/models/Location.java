package com.example.crowderapp.models;

import java.io.Serializable;

/**
 * Represents a location for a trial.
 */
public class Location implements Serializable {
    private double longitude;
    private double latitude;

    public Location() {
    }

    /**
     * gets the longitude of a trial
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * sets the longitude for a trial
     * @param longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * gets the latitude of a trial
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * sets the latitude for a trial
     * @param latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
