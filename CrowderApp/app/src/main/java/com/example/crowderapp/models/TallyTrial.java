package com.example.crowderapp.models;

import java.util.Date;

public class TallyTrial extends Trial {
    private int tally;

    public TallyTrial() {
    }

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
