package com.example.crowderapp.models;

import java.util.ArrayList;
import android.location.Location;

import java.util.Date;
import java.util.List;

public class CounterExperiment extends Experiment {

    private int totalCount;
    protected List<CounterTrial> trials = new ArrayList<CounterTrial>();

    public CounterExperiment() {
        super();
    }

    public CounterExperiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    @Override
    public List<? extends Trial> getTrials() {
        return trials;
    }

    @Override
    public ExperimentStats getStats() {
        return new CounterStats(trials);
    }

    public void incrementCount(String experimenter, Location location) {
        totalCount += 1;
        trials.add(new CounterTrial(experimenter, new Date(), location, this.getExperimentID()));
    }

}
