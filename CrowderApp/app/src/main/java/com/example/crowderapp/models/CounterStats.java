package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class CounterStats extends ExperimentStats<CounterTrial> {

    List<CounterTrial> trials;

    public CounterStats(List<CounterTrial> trials) {
        super(trials);
        this.trials = trials;
        Collections.sort(this.trials);
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
    protected List<Graph> createPlot() {
        SortedMap<Date, Integer> counts = new TreeMap<>();

        CounterTrial lastTrial = null;
        for (CounterTrial trial : trials) {
            if (lastTrial == null || trial.compareTo(lastTrial) != 0) {
                lastTrial = trial;
                counts.put(lastTrial.getDate(), 1);
            } else {
                counts.put(lastTrial.getDate(), counts.get(lastTrial.getDate())+1);
            }
        }

        List<Point> countList = new ArrayList<>();
        Set<Date> set = counts.keySet();
        for (Date key : set) {
            countList.add(new Point(key, counts.get(key)));
        }
        Graph countGraph = new Graph("Counts Per Day", countList);
        List<Graph> output = new ArrayList<>();
        output.add(countGraph);

        return output;
    }

    @Override
    protected List<Bar> createHistogram() {
        for (CounterTrial trial : trials) {

        }
        return null;
    }
}
