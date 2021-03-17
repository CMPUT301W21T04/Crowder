package com.example.crowderapp.models;

import java.util.Date;

public class MeasurementTrial extends Trial {
    public MeasurementTrial() {
    }

    private double measurement;

    public MeasurementTrial(String experimenter, Date date, double measurement, Location location, String experimentID) {
        super(experimenter, date, location, experimentID);
        this.measurement = measurement;
    }

    public void setMeasurement(double measurement) {
        this.measurement = measurement;
    }

    public double getMeasurement() {
        return measurement;
    }
}
