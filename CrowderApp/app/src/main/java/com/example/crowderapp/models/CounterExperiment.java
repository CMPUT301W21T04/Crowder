package com.example.crowderapp.models;

import java.util.Date;

public class CounterExperiment extends Experiment {

    private int totalCount;

    public CounterExperiment(int experimentID, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, int ownerID) {
        super(experimentID, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    public void incrementCount(String experimenter) {
        totalCount += 1;
        trials.add(new CounterTrial(experimenter, new Date()));
    }

}
