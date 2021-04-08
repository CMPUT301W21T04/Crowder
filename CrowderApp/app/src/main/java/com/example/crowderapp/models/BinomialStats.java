package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class BinomialStats extends ExperimentStats<BinomialTrial> {

    List<BinomialTrial> trials;

    public BinomialStats(List<BinomialTrial> trials) {
        super(trials);
        this.trials = trials;
        Collections.sort(this.trials);
    }

    @Override
    protected double[] setValues(List<BinomialTrial> trials) {
        double[] val = new double[trials.size()];
        int index = 0;
        for (BinomialTrial trial : trials) {
            if (trial.isResult()) { val[index] = 1;}
            else { val[index] = 0; }
            index++;
        }
        return val;
    }

    @Override
    protected List<Graph> createPlot() {
        SortedMap<Date, Integer> hashedPasses = new TreeMap<>();
        SortedMap<Date, Integer> hashedFails = new TreeMap<>();

        BinomialTrial lastPassedTrial = null;
        BinomialTrial lastFailedTrial = null;
        for (BinomialTrial trial : trials) {
            if (trial.isResult()) {
                if (lastPassedTrial == null || trial.compareTo(lastPassedTrial) != 0) {
                    lastPassedTrial = trial;
                    hashedPasses.put(lastPassedTrial.getDate(), 1);
                } else {
                    hashedPasses.put(lastPassedTrial.getDate(), hashedPasses.get(lastPassedTrial.getDate())+1);
                }
            } else {
                if (lastFailedTrial == null || trial.compareTo(lastFailedTrial) != 0) {
                    lastFailedTrial = trial;
                    hashedFails.put(lastFailedTrial.getDate(), 1);
                } else {
                    hashedFails.put(lastFailedTrial.getDate(), hashedFails.get(lastFailedTrial.getDate())+1);
                }
            }
        }

        List<Point> passes = new ArrayList<>();
        List<Point> fails = new ArrayList<>();
        Set<Date> setp = hashedPasses.keySet();
        for (Date key : setp) {
            passes.add(new Point(key, hashedPasses.get(key)));
        }
        Set<Date> setf = hashedFails.keySet();
        for (Date key : setf) {
            passes.add(new Point(key, hashedFails.get(key)));
        }

        Graph passGraph = new Graph("Passes per Day", passes);
        Graph failGraph = new Graph("Fails per Day", fails);
        List<Graph> output = new ArrayList<>();
        output.add(passGraph);
        output.add(failGraph);

        return output;
    }

    @Override
    protected List<Bar> createHistogram() {
        List<Bar> bars = new ArrayList<Bar>();
        int passes = 0;
        int fails = 0;
        for (BinomialTrial trial : trials) {
            if (trial.isResult()) { passes++;}
            else { fails++; }
        }
        bars.add(new Bar("Passes", passes));
        bars.add(new Bar("Fails", fails));
        return bars;
    }
}
