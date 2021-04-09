package com.example.crowderapp.models;

import java.util.Date;

/**
 * Has data for a measurement trial
 *
 */
public class MeasurementTrial extends Trial {
    public MeasurementTrial() {
    }

    private double measurement;

    public MeasurementTrial(String experimenter, Date date, double measurement, Location location, String experimentID) {
        super(experimenter, date, location, experimentID);
        this.measurement = measurement;
    }

    /**
     * Sets the measurement
     * @param measurement measurement value
     */
    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    /**
     * gets the measurement
     * @return measurement 
     */
    public double getMeasurement() {
        return measurement;
    }
}
