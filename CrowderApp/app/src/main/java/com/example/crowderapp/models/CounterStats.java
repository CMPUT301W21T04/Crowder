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
        int index = 0;

        CounterTrial lastTrial = trials.get(0);
        for (CounterTrial trial : trials) {
            if (lastTrial.compareTo(trial) != 0) {
                index++;
                lastTrial = trial;
            }
            val[index] += 1;
        }
        return val;
    }

    @Override
    protected List<Graph> createPlot(List<CounterTrial> trials) {
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
    protected List<Bar> createHistogram(List<CounterTrial> trials) {
        List<Bar> bars = new ArrayList<>();
        bars.add(new Bar("Total Count", trials.size()));
        return bars;
    }
}
