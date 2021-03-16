package com.example.crowderapp.models;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public abstract class ExperimentStats {
    protected double mean;
    protected double median;
    protected double stdev;
    protected List<Double> quartiles;
    protected List<Point> plotPoints;
    protected List<Point> histPoints;

    public class Point {
        private String x;
        private double y;

        public Point(String x, double y) {
            this.x = x;
            this.y = y;
        }

        private void setX(String x) { this.x = x; }
        private void setY(double y) { this.y = y; }

        public String getX() { return x; }
        public double getY() { return y; }
    }

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

    public List<Point> getPlotPoints() { return plotPoints; }

    public List<Point> getHistPoints() { return histPoints; }

    // for mean+med: https://stackoverflow.com/questions/4191687/how-to-calculate-mean-median-mode-and-range-from-a-set-of-numbers
    protected double calcMean(double[] values) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum /(double) values.length;
    }

    protected double calcMedian(double[] values) {
        int middle = values.length/2;
        if (values.length%2 == 1) {
            return values[middle];
        } else {
            return (values[middle-1] + values[middle]) / 2.0d;
        }
    }

    // https://stackoverflow.com/questions/18390548/how-to-calculate-standard-deviation-using-java
    // unit test this. Needs verification.
    protected double calcStdev(double[] values, double mean) {
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
    // unit test this. Needs verification.
    protected List<Double> calcQuart(double[] values, double median) {
        double[] firstHalf = new double[values.length/2];
        int firstIndex = 0;
        double[] secondHalf = new double[values.length/2];
        int secondIndex = 0;
        for (int index = 0; index < values.length; index++) {
            if (values[index] < median) {
                firstHalf[firstIndex] = values[index];
                firstIndex++;
            } else if ((values[index] > median)) {
                secondHalf[firstIndex] = values[index];
                secondIndex++;
            }
        }
        List<Double> quarts = new ArrayList<Double>();
        quarts.add(calcMedian(firstHalf));
        quarts.add(median);
        quarts.add(calcMedian(secondHalf));
        return quarts;
    }
}
