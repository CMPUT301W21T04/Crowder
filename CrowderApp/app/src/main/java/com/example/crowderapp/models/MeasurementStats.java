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
    protected List<Graph> createPlot(List<MeasurementTrial> trials) {
        List<Point> points = new ArrayList<>();
        for (MeasurementTrial trial : trials) {
            points.add(new Point(trial.getDate(), trial.getMeasurement()));
        }
        List<Graph> graphs = new ArrayList<>();
        graphs.add(new Graph("Measurements", points));
        return graphs;
    }

    @Override
    protected List<Bar> createHistogram(List<MeasurementTrial> trials) {
        List<Bar> bars = new ArrayList<>();
        int[] quarters = {0,0,0,0};
        for (MeasurementTrial trial : trials) {
            if (trial.getMeasurement() <= quartiles.get(0)) {
                quarters[0]++;
            } else if (trial.getMeasurement() <= quartiles.get(1)) {
                quarters[1]++;
            } else if (trial.getMeasurement() <= quartiles.get(2)) {
                quarters[2]++;
            } else {
                quarters[3]++;
            }
        }
        bars.add(new Bar("<"+quartiles.get(0).toString(), quarters[0]));
        bars.add(new Bar(quartiles.get(0).toString()+"-"+quartiles.get(1).toString(), quarters[1]));
        bars.add(new Bar(quartiles.get(1).toString()+"-"+quartiles.get(2).toString(), quarters[2]));
        bars.add(new Bar(">"+quartiles.get(2).toString(), quarters[3]));
        return bars;
    }
}
