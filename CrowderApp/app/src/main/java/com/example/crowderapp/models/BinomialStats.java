package com.example.crowderapp.models;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class BinomialStats extends ExperimentStats<BinomialTrial> {

    List<BinomialTrial> trials;

    public BinomialStats(List<BinomialTrial> trials) {
        super(trials);
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
    protected List<Point> createPlot() {

        return null;
    }

    @Override
    protected List<Point> createHistogram() {
        List<Point> bars = new ArrayList<Point>();
        int passes = 0;
        int fails = 0;
        for (BinomialTrial trial : trials) {
            if (trial.isResult()) { passes++;}
            else { fails++; }
        }
        bars.add(new Point("Passes", passes));
        bars.add(new Point("Fails", fails));
        return bars;
    }
}
