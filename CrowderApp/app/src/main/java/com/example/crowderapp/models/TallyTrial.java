package com.example.crowderapp.models;

import java.util.Date;

public class TallyTrial extends Trial {
    private int tally;

    public TallyTrial(String experimenter, Date date, int tally) {
        super(experimenter, date);
        this.tally = tally;
    }

    public int getTally () {
        return tally;
    }
}
