package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class ExperimentStats <T extends Trial> {
    protected double mean;
    protected double median;
    protected double stdev;
    protected double[] values;
    protected List<T> trials;
    protected List<Double> quartiles;
    protected Graph plotPoints;
    protected List<Bar> histPoints;

    public class Graph {
        private String name;
        private List<Point> points;

        public Graph(String name, List<Point> points) {
            this.name = name;
            this.points = points;
        }

        public String getName() { return name; }
        public List<Point> getPoints() { return points; }
    }

    public class Point {
        private Date x;
        private double y;

        public Point(Date x, double y) {
            this.x = x;
            this.y = y;
        }

        private void setX(Date x) { this.x = x; }
        private void setY(double y) { this.y = y; }

        public Date getX() { return x; }
        public double getY() { return y; }
    }

    public class Bar {
        private String x;
        private double y;

        public Bar(String x, double y) {
            this.x = x;
            this.y = y;
        }

        private void setX(String x) { this.x = x; }
        private void setY(double y) { this.y = y; }

        public String getX() { return x; }
        public double getY() { return y; }
    }

    // Template pattern
    ExperimentStats(List<T> trials) {
        this.trials = trials;
        Collections.sort(this.trials);
        values = setValues();
        mean = calcMean(values);
        median = calcMedian(values);
        stdev = calcStdev(values, mean);
        quartiles = calcQuart(values);
        plotPoints = createPlot();
        histPoints = createHistogram();
    }

    protected abstract double[] setValues();

    protected abstract Graph createPlot();

    protected abstract List<Bar> createHistogram();

    public double getMean() {
        return mean;
    }

    public double getMedian() {
        return median;
    }

    public double getStdev() {
        return stdev;
    }

    public List<Double> getQuartiles() {
        return quartiles;
    }

    public Graph getPlotPoints() { return plotPoints; }

    public List<Bar> getHistPoints() { return histPoints; }

    // for mean+med: https://stackoverflow.com/questions/4191687/how-to-calculate-mean-median-mode-and-range-from-a-set-of-numbers
    protected double calcMean(double[] values) {
        if (values.length == 0) {
            return 0d;
        }
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum /(double) values.length;
    }

    protected double calcMedian(double[] values) {
        if (values.length == 0) {
            return 0d;
        }
        Arrays.sort(values);
        int middle = values.length/2;
        if (values.length%2 == 1) {
            return values[middle];
        } else {
            return (values[middle-1] + values[middle]) / 2.0d;
        }
    }

    // https://stackoverflow.com/questions/18390548/how-to-calculate-standard-deviation-using-java
    protected double calcStdev(double[] values, double mean) {
        if (values.length < 2) {
            return Double.NaN;
        }
        double[] val = new double[values.length];
        for (int index = 0; index < values.length; index++) {
            val[index] = Math.pow(values[index]-mean, 2);
        }
        double sum = 0;
        for (int index = 0; index < val.length; index++) {
            sum += val[index];
        }
        return Math.pow(sum/(double) val.length, 0.5d);
    }

    // https://stackoverflow.com/questions/42381759/finding-first-quartile-and-third-quartile-in-integer-array-using-java
    protected List<Double> calcQuart(double[] val) {
        if (val.length < 4) {
            List<Double> output = new ArrayList<Double>();
            output.add(Double.NaN);
            output.add(Double.NaN);
            output.add(Double.NaN);
            return output;
        }
        Arrays.sort(val);
        Double ans[] = new Double[3];

        for (int quartileType = 1; quartileType < 4; quartileType++) {
            float length = val.length + 1;
            double quartile;
            float newArraySize = (length * ((float) (quartileType) * 25 / 100)) - 1;
            Arrays.sort(val);
            if (newArraySize % 1 == 0) {
                quartile = val[(int) (newArraySize)];
            } else {
                int newArraySize1 = (int) (newArraySize);
                quartile = (val[newArraySize1] + val[newArraySize1 + 1]) / 2;
            }
            ans[quartileType - 1] =  quartile;
        }
        List<Double> output = new ArrayList<>(Arrays.asList(ans));
        return output;
    }

    protected int daysDiff(Date d1, Date d2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d2);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        Date other = cal.getTime();
        long diff = other.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
