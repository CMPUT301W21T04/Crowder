package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class TallyStats extends ExperimentStats<TallyTrial> {

    public TallyStats(List<TallyTrial> trials) {
        super(trials);
    }

    @Override
    protected double[] setValues() {
        double[] val = new double[trials.size()];
        int index = 0;
        for (TallyTrial trial : trials) {
            val[index] = trial.getTally();
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
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);

        int index = 0;
        List<Point> points = new ArrayList<>();
        double[] maxMeas = new double[trials.size()];
        double[] measActual;
        int countPerDay = 0;
        //https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
        //Answered by BalusC
        //License CC BY-SA 3.0
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            while (index < trials.size() && daysDiff(date, trials.get(index).getDate()) == 0) {
                maxMeas[countPerDay] = (double)trials.get(index).getTally();
                countPerDay++;
                index++;
            }
            measActual = Arrays.copyOfRange(maxMeas, 0, countPerDay);
            points.add(new Point(date, calcMean(measActual)));
            countPerDay = 0;
        }
        return new Graph("Mean Measurement Over Time", points);
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
