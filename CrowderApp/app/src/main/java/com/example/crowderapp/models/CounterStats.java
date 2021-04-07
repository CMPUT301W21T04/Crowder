package com.example.crowderapp.models;

import java.security.Policy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class CounterStats extends ExperimentStats {
    public CounterStats(List<CounterTrial> trials) {
        Collections.sort(trials);

        double[] values = new double[trials.size()];
        Hashtable<String, Double> trialsByDate = new Hashtable<String, Double>();
        List<Point> points = new ArrayList<Point>();
        int index = 0;

        Calendar cal = Calendar.getInstance();

        CounterTrial lastTrial = trials.get(0);
        for (CounterTrial trial : trials) {
            if (lastTrial.compareTo(trial) != 0) {
                cal.setTime(trial.getDate());
                trialsByDate.put(String.valueOf(cal.MONTH)+" "+String.valueOf(cal.DAY_OF_MONTH), values[index]);
                index++;
                lastTrial = trial;
            }
            values[index] += 1;
        }

        //make points

        mean = calcMean(values);
        median = calcMedian(values);
        stdev = calcStdev(values, mean);
        quartiles = calcQuart(values);

        //same for both. Literally just trialsByDate
        createPlot();
        createHistogram();
    }

    protected void createPlot() {

    }

    protected void createHistogram() {

    }
}
