package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CounterExperiment extends Experiment {

    private int totalCount;
    protected List<CounterTrial> trials = new ArrayList<CounterTrial>();

    /**
     * Constructor
     */
    public CounterExperiment() {
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
    public CounterExperiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    /*@Override
    public List<? extends Trial> getTrials() {
        return trials;
    }*/

    @Override
    public ExperimentStats getStats() {
        return new CounterStats(trials);
    }

    /**
     * Adds a count trial to the experiment
     * @param experimenter
     * @param location
     */
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
