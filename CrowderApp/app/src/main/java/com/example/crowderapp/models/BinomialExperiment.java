package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BinomialExperiment extends Experiment {

    private int totalPass = 0;
    private int totalFail = 0;
    protected List<BinomialTrial> trials = new ArrayList<BinomialTrial>();

    public BinomialExperiment() {
        super();
    }

    public BinomialExperiment(String experimentID, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, int ownerID) {
        super(experimentID, minTrials, isEnded, isUnpublished, isLocationRequired, ownerID);
    }

    @Override
    public List<? extends Trial> getTrials() {
        return trials;
    }

    @Override
    public ExperimentStats getStats() {
        return new BinomialStats(trials);
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
