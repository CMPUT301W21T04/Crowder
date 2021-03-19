package com.example.crowderapp.models;

import java.util.Date;

/**
 * Object to add trials to a Tally experiment
 */
public class TallyTrial extends Trial {
    private int tally;

    public TallyTrial() {
    }

    /**
     * Constructor
     * @param experimenter unique id of experimenter
     * @param date the date trial was taken
     * @param tally the count that was observed
     * @param location the location the trial was taken
     * @param experimentID the unique id of the experiment
     */
    public TallyTrial(String experimenter, Date date, int tally, Location location, String experimentID) {
        super(experimenter, date, location, experimentID);
        this.tally = tally;
    }

    public void setTally(int tally) {
        this.tally = tally;
    }

    public int getTally () {
        return tally;
    }
}
