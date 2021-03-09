package com.example.crowderapp.models;

import java.util.Date;

public class BinomialExperiment extends Experiment {

    private int totalPass = 0;
    private int totalFail = 0;

    public BinomialExperiment(int experimentID, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, int ownerID) {
        super(experimentID, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    public void addPass(String experimenter) {
        totalPass += 1;
        trials.add(new BinomialTrial(experimenter, new Date(), true));
    }

    public void AddFail(String experimenter) {
        totalFail += 1;
        trials.add(new BinomialTrial(experimenter, new Date(), false));
    }
}
