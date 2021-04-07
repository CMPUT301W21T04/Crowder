package com.example.crowderapp.models;

import java.util.List;

public class TallyStats extends ExperimentStats<TallyTrial> {

    List<TallyTrial> trials;

    public TallyStats(List<TallyTrial> trials) {
        super(trials);
    }

    @Override
    protected double[] setValues(List<TallyTrial> trials) {
        double[] val = new double[trials.size()];
        int index = 0;
        for (TallyTrial trial : trials) {
            val[index] = trial.getTally();
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
