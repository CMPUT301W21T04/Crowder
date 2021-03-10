package com.example.crowderapp.models;

import java.util.Date;

public class TallyExperiment extends Experiment {

    private double avgTally;
    private int tallyCount;

    public TallyExperiment(String experimentID, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, int ownerID) {
        super(experimentID, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    public void addNonNegativeCount(int count, String experimenter) {
        avgTally = (avgTally*tallyCount+count)/(1+tallyCount);
        tallyCount += 1;
        trials.add(new TallyTrial(experimenter, new Date(), count));
    }
}
