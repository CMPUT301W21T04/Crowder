package com.example.crowderapp.models;

import android.location.Location;

import java.util.Date;

public class MeasurementTrial extends Trial {
    private double measurement;

    public MeasurementTrial(String experimenter, Date date, double measurement, Location location, String experimentID) {
        super(experimenter, date, location, experimentID);
        this.measurement = measurement;
    }

    public double getMeasurement() {
        return measurement;
    }
}
