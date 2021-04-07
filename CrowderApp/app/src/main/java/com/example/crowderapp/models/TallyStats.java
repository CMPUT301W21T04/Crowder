package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class TallyStats extends ExperimentStats<TallyTrial> {

    List<TallyTrial> trials;

    public TallyStats(List<TallyTrial> trials) {
        super(trials);
        this.trials = trials;
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
    protected List<Graph> createPlot() {
        List<Point> points = new ArrayList<>();
        for (TallyTrial trial : trials) {
            points.add(new Point(trial.getDate(), trial.getTally()));
        }
        List<Graph> graphs = new ArrayList<>();
        graphs.add(new Graph("Measurements", points));
        return graphs;
    }

    @Override
    protected List<Bar> createHistogram() {
        SortedMap<Integer, Integer> counts = new TreeMap<>();
        for (TallyTrial trial : trials) {
            if (counts.containsKey(trial.getTally())) {
                counts.put(trial.getTally(), counts.get(trial.getTally())+1);
            }
            else {
                counts.put(trial.getTally(), 1);
            }
        }
        List<Bar> bars = new ArrayList<>();
        Set<Integer> set = counts.keySet();
        for (Integer in : set) {
            bars.add(new Bar(in.toString(), counts.get(in)));
        }
        return bars;
    }
}
