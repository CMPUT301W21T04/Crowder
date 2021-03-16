package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CounterExperiment extends Experiment {

    private int totalCount;
    protected List<CounterTrial> trials = new ArrayList<CounterTrial>();

    public CounterExperiment() {
        super();
    }

    public CounterExperiment(String experimentID, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, int ownerID) {
        super(experimentID, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    @Override
    public List<? extends Trial> getTrials() {
        return trials;
    }

    @Override
    public ExperimentStats getStats() {
        return new CounterStats(trials);
    }

    public void incrementCount(String experimenter) {
        totalCount += 1;
        trials.add(new CounterTrial(experimenter, new Date()));
    }

}
