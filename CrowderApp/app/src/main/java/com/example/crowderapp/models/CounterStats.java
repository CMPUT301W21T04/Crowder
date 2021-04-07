package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

public class CounterStats extends ExperimentStats<CounterTrial> {

    List<CounterTrial> trials;

    public CounterStats(List<CounterTrial> trials) {
        super(trials);
    }

    @Override
    protected double[] setValues(List<CounterTrial> trials) {
        Collections.sort(trials);

        double[] val = new double[trials.size()];
        Hashtable<String, Double> trialsByDate = new Hashtable<String, Double>();
        List<Point> points = new ArrayList<Point>();
        int index = 0;

        Calendar cal = Calendar.getInstance();

        CounterTrial lastTrial = trials.get(0);
        for (CounterTrial trial : trials) {
            if (lastTrial.compareTo(trial) != 0) {
                cal.setTime(trial.getDate());
                trialsByDate.put(String.valueOf(cal.MONTH)+" "+String.valueOf(cal.DAY_OF_MONTH), val[index]);
                index++;
                lastTrial = trial;
            }
            val[index] += 1;
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
