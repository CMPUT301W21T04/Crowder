package com.example.crowderapp.models;

import android.location.Location;

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
}
