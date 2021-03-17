package com.example.crowderapp.models;

import java.util.ArrayList;
import android.location.Location;

import java.util.Date;
import java.util.List;

public class BinomialExperiment extends Experiment {

    private int totalPass = 0;
    private int totalFail = 0;
    protected List<BinomialTrial> trials = new ArrayList<BinomialTrial>();

    public BinomialExperiment() {
        super();
    }

    public BinomialExperiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        super(experimentID, name, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    @Override
    public List<? extends Trial> getTrials() {
        return trials;
    }

    @Override
    public ExperimentStats getStats() {
        return new BinomialStats(trials);
    }

    public void addPass(String experimenter, Location location) {
        totalPass += 1;
        trials.add(new BinomialTrial(experimenter, new Date(), true, location, this.getExperimentID()));
    }

    public void AddFail(String experimenter, Location location) {
        totalFail += 1;
        trials.add(new BinomialTrial(experimenter, new Date(), false, location, this.getExperimentID()));
    }
}
