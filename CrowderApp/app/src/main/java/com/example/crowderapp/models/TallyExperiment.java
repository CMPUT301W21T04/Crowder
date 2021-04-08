package com.example.crowderapp.models;

import java.util.Date;

/**
 * Class to contain experiment of type Non-negative Integer Trial
 */
public class TallyExperiment extends Experiment {

    private double avgTally;
    private int tallyCount;

    public TallyExperiment() {
        super();
    }

    public TallyExperiment(String experimentID, String name, String region, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, region, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    /**
     * Adds a trial to the experiment
     * @param count the observed count to add
     * @param experimenter unique identifier of the experimenter adding the count
     * @param location the location where the trial was taken
     */
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
