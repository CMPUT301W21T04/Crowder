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

    public CounterStats(List<CounterTrial> trials) {
        super(trials);
    }

    @Override
    protected double[] setValues() {
        if (trials.size() == 0) {
            return new double[0];
        }
        double[] val = new double[daysDiff(trials.get(0).getDate(), trials.get(trials.size()-1).getDate())+1];

        Calendar start = Calendar.getInstance();
        start.setTime(trials.get(0).getDate());
        Calendar end = Calendar.getInstance();
        end.setTime(trials.get(trials.size()-1).getDate());
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);

        int trialsIndex = 0;
        int dateIndex = 0;
        double count = 0;
        //https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            while (trialsIndex < trials.size() && daysDiff(date, trials.get(trialsIndex).getDate()) == 0) {
                count++;
                trialsIndex++;
            }
            val[dateIndex] = count;
            count = 0;
            dateIndex++;
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
        int count = 0;
        //https://stackoverflow.com/questions/4534924/how-to-iterate-through-range-of-dates-in-java
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            while (index < trials.size() && daysDiff(date, trials.get(index).getDate()) == 0) {
                count++;
                index++;
            }
            points.add(new Point(date, count));
            count = 0;
        }
        return new Graph("Change in Count Per Day", points);
    }

    @Override
    protected List<Bar> createHistogram() {
        List<Bar> bars = new ArrayList<>();
        bars.add(new Bar("Total Count", trials.size()));
        return bars;
    }
}
