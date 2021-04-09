package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MeasurementStats extends ExperimentStats<MeasurementTrial> {

    public MeasurementStats(List<MeasurementTrial> trials) {
        super(trials);
    }

    @Override
    protected double[] setValues() {
        double[] val = new double[trials.size()];
        int index = 0;
        for (MeasurementTrial trial : trials) {
            val[index] = trial.getMeasurement();
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
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            while (index < trials.size() && daysDiff(date, trials.get(index).getDate()) == 0) {
                maxMeas[countPerDay] = trials.get(index).getMeasurement();
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
        List<Bar> bars = new ArrayList<>();
        int[] quarters = {0,0,0,0};
        for (MeasurementTrial trial : trials) {
            if (trial.getMeasurement() <= quartiles.get(0)) {
                quarters[0]++;
            } else if (trial.getMeasurement() <= quartiles.get(1)) {
                quarters[1]++;
            } else if (trial.getMeasurement() <= quartiles.get(2)) {
                quarters[2]++;
            } else {
                quarters[3]++;
            }
        }
        bars.add(new Bar("<"+quartiles.get(0).toString(), quarters[0]));
        bars.add(new Bar(quartiles.get(0).toString()+"-"+quartiles.get(1).toString(), quarters[1]));
        bars.add(new Bar(quartiles.get(1).toString()+"-"+quartiles.get(2).toString(), quarters[2]));
        bars.add(new Bar(">"+quartiles.get(2).toString(), quarters[3]));
        return bars;
    }
}
