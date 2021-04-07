package com.example.crowderapp.models;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

public class BinomialExperiment extends Experiment {

    private int totalPass = 0;
    private int totalFail = 0;
    protected List<BinomialTrial> trials = new ArrayList<BinomialTrial>();
    private double successRate = 0;

    /**
     * Constructor
     */
    public BinomialExperiment() {
        super();
    }

    /**
     * Constructor
     * @param experimentID Experiment ID
     * @param name Name of experiment
     * @param minTrials Minimum number of trials
     * @param isEnded Boolean to show if the experiment is ended or not
     * @param isUnpublished Boolean to show if the experiment has been unpublished
     * @param isLocationRequired Boolean to show if the location is required to add trials
     * @param ownerID ID of the user who created the experiment
     */
    public BinomialExperiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
        this.totalPass = 0;
        this.totalFail = 0;
        this.successRate = 0;
    }

    /*@Override
    public List<? extends Trial> getTrials() {
        return trials;
    }*/

    @Override
    public ExperimentStats getStats() {
        return new BinomialStats(trials);
    }
    /**
     * Adds a pass trial to the experiment
     * @param experimenter ID of experimenter
     * @param location Location of the trial
     */
    public void addPass(String experimenter, Location location) {
        totalPass += 1;
        updateSuccessRate();
        trials.add(new BinomialTrial(experimenter, new Date(), true, location, this.getExperimentID()));
    }

    /**
     * Adds a fail trial to the experiment
     * @param experimenter ID of the experimenter
     * @param location Location of the trial
     */
    public void addFail(String experimenter, Location location) {
        totalFail += 1;
        updateSuccessRate();
        trials.add(new BinomialTrial(experimenter, new Date(), false, location, this.getExperimentID()));
    }

    /**
     * Updates the success rate
     */
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
