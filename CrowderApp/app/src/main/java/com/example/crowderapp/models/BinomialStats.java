package com.example.crowderapp.models;

import java.util.List;

public class BinomialStats extends ExperimentStats {
    public BinomialStats(List<BinomialTrial> trials) {
        double[] values = new double[trials.size()];
        int index = 0;
        for (BinomialTrial trial : trials) {
            if (trial.isResult()) { values[index] = 1;}
            else { values[index] = 0; }
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
