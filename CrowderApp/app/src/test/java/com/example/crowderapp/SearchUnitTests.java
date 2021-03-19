package com.example.crowderapp;

import android.widget.ArrayAdapter;

import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Search;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SearchUnitTests {

    Search searcher;
    Experiment exp;
    Experiment exp_too;
    ArrayList<Experiment> l;

    @Before
    public void setUp() throws Exception {
        searcher = new Search();
        exp = new Experiment();
        exp.setName("TESTNAME");
        exp.setExperimentType("Binomial");

        exp_too = new Experiment();
        exp_too.setName("TESTNAME2");
        exp_too.setExperimentType("Count");

        l = new ArrayList<>();
        l.add(exp);
        l.add(exp_too);
    }

    @Test
    public void testBothMatch() {
        ArrayList<String> fStrings = new ArrayList<>();
        fStrings.add("TEST");
        List<Experiment> r = searcher.searchExperiments(fStrings, l);

        // Both match
        Assert.assertEquals(r.get(0), exp);
        Assert.assertEquals(r.get(1), exp_too);
    }

    @Test
    public void testNoMatch() {
        ArrayList<String> fStrings = new ArrayList<>();
        fStrings.add("None");
        List<Experiment> r = searcher.searchExperiments(fStrings, l);

        // Both match
        Assert.assertEquals(r.size(), 0);
    }

    @Test
    public void testSearchType() {
        ArrayList<String> fStrings = new ArrayList<>();
        fStrings.add("Count");
        List<Experiment> r = searcher.searchExperiments(fStrings, l);

        // Both match
        Assert.assertEquals("Count", r.get(0).getExperimentType());
    }
}
