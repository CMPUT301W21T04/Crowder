package com.example.crowderapp.models;

import java.util.Date;

public class MeasurementTrial extends Trial {
    private double measurement;

    public MeasurementTrial(String experimenter, Date date, double measurement) {
        super(experimenter, date);
        this.measurement = measurement;
    }

    public double getMeasurement() {
        return measurement;
    }
}
