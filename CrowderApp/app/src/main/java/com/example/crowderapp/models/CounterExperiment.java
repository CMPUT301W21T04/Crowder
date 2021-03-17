package com.example.crowderapp.models;

import java.util.Date;

public class CounterExperiment extends Experiment {

    private int totalCount;

    public CounterExperiment() {
        super();
    }

    public CounterExperiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    public void incrementCount(String experimenter, Location location) {
        totalCount += 1;
        trials.add(new CounterTrial(experimenter, new Date(), location, this.getExperimentID()));
    }

    /**
     * @return The sum of all the counts.
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Set the sum of all the counts
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
