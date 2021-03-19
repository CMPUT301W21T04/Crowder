package com.example.crowderapp.models;

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

    /**
     * @return The average of all the tallies.
     */
    public double getAvgTally() {
        return avgTally;
    }

    /**
     * Directly set the average of all tallies.
     * @param avgTally
     */
    public void setAvgTally(double avgTally) {
        this.avgTally = avgTally;
    }

    /**
     * @return The sum of all tallies.
     */
    public int getTallyCount() {
        return tallyCount;
    }

    /**
     * Set the sum of all tallies.
     * @param tallyCount
     */
    public void setTallyCount(int tallyCount) {
        this.tallyCount = tallyCount;
    }
}
