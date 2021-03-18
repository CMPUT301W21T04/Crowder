package com.example.crowderapp.models;


import java.util.Date;

public class BinomialTrial extends Trial {
    private boolean result;

    public BinomialTrial(String experimenter, Date date, boolean result, Location location, String experimentID) {
        super(experimenter, date, location, experimentID);
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
