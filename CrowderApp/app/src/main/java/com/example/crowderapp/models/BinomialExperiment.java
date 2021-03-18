package com.example.crowderapp.models;


import java.util.Date;

public class BinomialExperiment extends Experiment {

    private int totalPass = 0;
    private int totalFail = 0;
    private double successRate = 0;

    public BinomialExperiment() {
        super();
    }

    public BinomialExperiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
        this.totalPass = 0;
        this.totalFail = 0;
        this.successRate = 0;
    }

    public void addPass(String experimenter, Location location) {
        totalPass += 1;
        updateSuccessRate();
        trials.add(new BinomialTrial(experimenter, new Date(), true, location, this.getExperimentID()));
    }

    public void addFail(String experimenter, Location location) {
        totalFail += 1;
        updateSuccessRate();
        trials.add(new BinomialTrial(experimenter, new Date(), false, location, this.getExperimentID()));
    }

    // Update the success rate
    private void updateSuccessRate() {
        // Removes possibility of divide by 0
        if (this.totalPass + this.totalFail == 0) {
            this.successRate = 0;
        } else {
            this.successRate = new Double(Double.valueOf(this.totalPass) / (Double.valueOf(this.totalPass) + Double.valueOf(this.totalFail))) * 100;
        }
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
