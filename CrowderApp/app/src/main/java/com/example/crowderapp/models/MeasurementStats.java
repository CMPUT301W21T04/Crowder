package com.example.crowderapp.models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeasurementStats extends ExperimentStats<MeasurementTrial> {

    List<MeasurementTrial> trials;

    public MeasurementStats(List<MeasurementTrial> trials) {
        super(trials);
    }

    @Override
    protected double[] setValues(List<MeasurementTrial> trials) {
        double[] val = new double[trials.size()];
        int index = 0;
        for (MeasurementTrial trial : trials) {
            val[index] = trial.getMeasurement();
            index++;
        }
        return val;
    }

    @Override
    protected List<Point> createPlot() {
        return null;
    }

    @Override
    protected List<Point> createHistogram() {
        return null;
    }
}
