package com.example.crowderapp.models;

import java.util.List;

public class TallyStats extends ExperimentStats {
    public TallyStats(List<TallyTrial> trials) {
        double[] values = new double[trials.size()];
        int index = 0;
        for (TallyTrial trial : trials) {
            values[index] = trial.getTally();
            index++;
        }
        mean = calcMean(values);
        median = calcMedian(values);
        stdev = calcStdev(values, mean);
        quartiles = calcQuart(values);
        createPlot();
        createHistogram();
    }

    protected void createPlot() {

    }

    protected void createHistogram() {

    }
}
