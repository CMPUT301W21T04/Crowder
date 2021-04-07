package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MeasurementStats extends ExperimentStats<MeasurementTrial> {

    List<MeasurementTrial> trials;

    public MeasurementStats(List<MeasurementTrial> trials) {
        super(trials);
        this.trials = trials;
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
    protected List<Graph> createPlot() {
        List<Point> points = new ArrayList<>();
        for (MeasurementTrial trial : trials) {
            points.add(new Point(trial.getDate(), trial.getMeasurement()));
        }
        List<Graph> graphs = new ArrayList<>();
        graphs.add(new Graph("Measurements", points));
        return graphs;
    }

    @Override
    protected List<Bar> createHistogram() {
        return null;
    }
}
