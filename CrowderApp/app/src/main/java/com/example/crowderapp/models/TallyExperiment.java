package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TallyExperiment extends Experiment {

    private double avgTally;
    private int tallyCount;
    protected List<TallyTrial> trials = new ArrayList<TallyTrial>();

    public TallyExperiment() {
        super();
    }

    public TallyExperiment(String experimentID, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, int ownerID) {
        super(experimentID, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    @Override
    public List<? extends Trial> getTrials() {
        return trials;
    }

    @Override
    public ExperimentStats getStats() {
        return new TallyStats(trials);
    }

    public void addNonNegativeCount(int count, String experimenter) {
        avgTally = (avgTally*tallyCount+count)/(1+tallyCount);
        tallyCount += 1;
        trials.add(new TallyTrial(experimenter, new Date(), count));
    }
}
