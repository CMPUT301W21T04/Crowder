package com.example.crowderapp.models;

import java.util.List;

public class MockExpStats extends ExperimentStats {

    public double calcMean(double[] values) {
        return super.calcMean(values);
    }

    public double calcMedian(double[] values) {
        return super.calcMedian(values);
    }

    public double calcStdev(double[] values, double mean) {
        return super.calcStdev(values, mean);
    }

    public List<Double> calcQuart(double[] values) {
        return super.calcQuart(values);
    }
}
