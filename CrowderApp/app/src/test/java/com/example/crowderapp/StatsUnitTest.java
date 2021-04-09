package com.example.crowderapp;

import com.example.crowderapp.models.ExperimentStats;
import com.example.crowderapp.models.MockExpStats;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.posts.Question;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StatsUnitTest {

    MockExpStats stats;

    @Before
    public void setUp() throws Exception {
        stats = new MockExpStats(new ArrayList<Trial>());
    }

    @Test
    public void testMean() {
        double[] data = {2d, 500.5d, 30d};
        Assert.assertEquals(177.5d, stats.calcMean(data), 0.001d);
    }

    @Test
    public void testMeanZero() {
        double[] data = {0d, 0d, 0d, 0d};
        Assert.assertEquals(0d, stats.calcMean(data), 0.001d);
    }

    @Test
    public void testMeanEmpty() {
        double[] data = {};
        Assert.assertEquals(0d, stats.calcMean(data), 0.001d);
    }

    @Test
    public void testMeanNegative() {
        double[] data = {-1d, 2d, -500d};
        Assert.assertEquals(-166.3333d, stats.calcMean(data), 0.001d);
    }

    @Test
    public void testMedian() {
        double[] data = {1.5d, 300d, 500d};
        Assert.assertEquals(300d, stats.calcMedian(data), 0.001d);
    }

    @Test
    public void testMedianEven() {
        double[] data = {1d, 1d, 2d, 6d, 6d, 9d};
        Assert.assertEquals(4d, stats.calcMedian(data), 0.001d);
    }

    @Test
    public void testMedianNegative() {
        double[] data = {1.5d, -300d, 500d};
        Assert.assertEquals(1.5d, stats.calcMedian(data), 0.001d);
    }

    @Test
    public void testMedianEmpty() {
        double[] data = {};
        Assert.assertEquals(0d, stats.calcMedian(data), 0.001d);
    }

    @Test
    public void testStdev() {
        double[] data = {10d, 12d, 23d, 23d, 16d, 23d, 21d, 16d};
        double mean = 18d;
        Assert.assertEquals(4.898979d, stats.calcStdev(data, mean), 0.001d);
    }

    @Test
    public void testStdevSame() {
        double[] data = {12d, 12d, 12d, 12d, 12d, 12d, 12d};
        double mean = 12d;
        Assert.assertEquals(0d, stats.calcStdev(data, mean), 0.001d);
    }

    @Test
    public void testStdevNegative() {
        double[] data = {10d, 12d, 23d, 23d, 16d, 23d, 21d, 16d};
        double mean = 18d;
        Assert.assertEquals(4.898979d, stats.calcStdev(data, mean), 0.001d);
    }

    @Test
    public void testStdevEmpty() {
        double[] data = {};
        double mean = 0d;
        Assert.assertEquals(Double.NaN, stats.calcStdev(data, mean), 0.001d);
    }

    @Test
    public void testQuart() {
        double[] data = {10d, 12d, 23d, 23d, 16d, 23d, 21d, 16d};
        double median = 18.5d;
        List<Double> expected = new ArrayList<Double>();
        expected.add(14d);
        expected.add(18.5d);
        expected.add(23d);
        List<Double> actual = stats.calcQuart(data);
        Assert.assertEquals(expected.get(0), actual.get(0), 0.001d);
        Assert.assertEquals(expected.get(1), actual.get(1), 0.001d);
        Assert.assertEquals(expected.get(2), actual.get(2), 0.001d);
    }

    @Test
    public void testQuartSameish() {
        double[] data = {10d, 10d, 10d, 11d};
        double median = 10d;
        List<Double> expected = new ArrayList<Double>();
        expected.add(10d);
        expected.add(10d);
        expected.add(10.5d);
        List<Double> actual = stats.calcQuart(data);
        Assert.assertEquals(expected.get(0), actual.get(0), 0.001d);
        Assert.assertEquals(expected.get(1), actual.get(1), 0.001d);
        Assert.assertEquals(expected.get(2), actual.get(2), 0.001d);
    }

    @Test
    public void testQuartNegative() {
        double[] data = {10d, 12d, -23d, 23d, -16d, 23d, 21d, 16d};
        double median = 14d;
        List<Double> expected = new ArrayList<Double>();
        expected.add(-3d);
        expected.add(14d);
        expected.add(22d);
        List<Double> actual = stats.calcQuart(data);
        Assert.assertEquals(expected.get(0), actual.get(0), 0.001d);
        Assert.assertEquals(expected.get(1), actual.get(1), 0.001d);
        Assert.assertEquals(expected.get(2), actual.get(2), 0.001d);
    }

    @Test
    public void testQuartEmpty() {
        double[] data = {};
        double median = 0d;
        List<Double> expected = new ArrayList<Double>();
        expected.add(Double.NaN);
        expected.add(Double.NaN);
        expected.add(Double.NaN);
        List<Double> actual = stats.calcQuart(data);
        Assert.assertEquals(expected.get(0), actual.get(0), 0.001d);
        Assert.assertEquals(expected.get(1), actual.get(1), 0.001d);
        Assert.assertEquals(expected.get(2), actual.get(2), 0.001d);
    }
}
