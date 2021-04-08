package com.example.crowderapp.models;

import java.util.List;

public class MockExpStats extends ExperimentStats<Trial> {

    public MockExpStats(List<Trial> trials) {
        super(trials);
    }

    @Override
    protected double[] setValues(List trials) {
        return new double[0];
    }

    @Override
    protected List<Graph> createPlot() {
        return null;
    }

    @Override
    protected List<Bar> createHistogram() {
        return null;
    }

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
