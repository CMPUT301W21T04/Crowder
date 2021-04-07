package com.example.crowderapp.models;

public class AllExperimentListItem {
    private Experiment experiment;
    private boolean isSubscribed;

    /**
     * Constructor
     * @param exp Experiment in the list
     * @param isSubscribed Boolean to show if the user is subscribed to the experiment
     */
    public AllExperimentListItem(Experiment exp, boolean isSubscribed) {
        this.experiment = exp;
        this.isSubscribed = isSubscribed;
    }

    public Experiment getExperiment() {return this.experiment;}

    public boolean getIsSubscribed() {return this.isSubscribed;}
}
