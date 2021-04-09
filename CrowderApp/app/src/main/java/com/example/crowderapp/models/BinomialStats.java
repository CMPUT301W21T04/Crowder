package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class BinomialStats extends ExperimentStats<BinomialTrial> {

    public BinomialStats(List<BinomialTrial> trials) {
        super(trials);
    }

    @Override
    protected double[] setValues() {
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
    protected Graph createPlot() {
        if (trials.size() == 0) {
            List<Point> points = new ArrayList<>();
            points.add(new Point(new Date(), 0d));
            return new Graph("", points);
        }
        Calendar start = Calendar.getInstance();
        start.setTime(trials.get(0).getDate());
        Calendar end = Calendar.getInstance();
        end.setTime(trials.get(trials.size()-1).getDate());
        end.set(Calendar.HOUR_OF_DAY,23);
        end.set(Calendar.MINUTE,59);
        end.set(Calendar.SECOND,59);

        int index = 0;
        List<Point> points = new ArrayList<>();
        double cumPasses = 0;
        double cumFails = 0;
        //https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            while (index < trials.size() && daysDiff(date, trials.get(index).getDate()) == 0) {
                if (trials.get(index).isResult()) {
                    cumPasses+=1d;
                } else {
                    cumFails+=1d;
                }
                index++;
            }
            points.add(new Point(date, cumPasses/(cumFails+cumPasses)));
        }
        return new Graph("Success Rate Over Time", points);
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
