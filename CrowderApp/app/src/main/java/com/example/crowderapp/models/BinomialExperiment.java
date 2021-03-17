package com.example.crowderapp.models;

import java.util.Date;

public class BinomialExperiment extends Experiment {

    private int totalPass = 0;
    private int totalFail = 0;

    public BinomialExperiment() {
        super();
    }

    public BinomialExperiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    public void addPass(String experimenter, Location location) {
        totalPass += 1;
        trials.add(new BinomialTrial(experimenter, new Date(), true, location, this.getExperimentID()));
    }

    public void AddFail(String experimenter, Location location) {
        totalFail += 1;
        trials.add(new BinomialTrial(experimenter, new Date(), false, location, this.getExperimentID()));
    }

    /**
     * Gets the sum of failure trials.
     * @return
     */
    public int getTotalFail() {
        return totalFail;
    }

    /**
     * Gets the passes, which are sum of successful trials.
     * @return
     */
    public int getTotalPass() {
        return totalPass;
    }
}
