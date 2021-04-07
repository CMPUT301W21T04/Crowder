package com.example.crowderapp.models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeasurementStats extends ExperimentStats {
    public MeasurementStats(List<MeasurementTrial> trials) {
        double[] values = new double[trials.size()];
        int index = 0;
        for (MeasurementTrial trial : trials) {
            values[index] = trial.getMeasurement();
            index++;
        }
        mean = calcMean(values);
        median = calcMedian(values);
        stdev = calcStdev(values, mean);
        quartiles = calcQuart(values);
        createPlot(trials);
        createHistogram(trials);
    }

    protected void createPlot(List<MeasurementTrial> trials) {
    }

    protected void createHistogram(List<MeasurementTrial> trials) {

    }
}
