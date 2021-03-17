package com.example.crowderapp.models;

public class AllExperimentListItem {
    private Experiment experiment;
    private boolean isSubscribed;

    public AllExperimentListItem(Experiment exp, boolean isSubscribed) {
        this.experiment = exp;
        this.isSubscribed = isSubscribed;
    }

    public Experiment getExperiment() {return this.experiment;}

    public boolean getIsSubscribed() {return this.isSubscribed;}
}
