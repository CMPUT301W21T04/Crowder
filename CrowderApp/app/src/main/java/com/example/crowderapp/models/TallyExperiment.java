package com.example.crowderapp.models;

import android.location.Location;

import java.util.Date;

public class TallyExperiment extends Experiment {

    private double avgTally;
    private int tallyCount;

    public TallyExperiment() {
        super();
    }

    public TallyExperiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    public void addNonNegativeCount(int count, String experimenter, Location location) {
        avgTally = (avgTally*tallyCount+count)/(1+tallyCount);
        tallyCount += 1;
        trials.add(new TallyTrial(experimenter, new Date(), count, location, this.getExperimentID()));
    }
}
