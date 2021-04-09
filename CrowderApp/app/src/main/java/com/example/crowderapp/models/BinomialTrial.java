package com.example.crowderapp.models;


import java.util.Date;

public class BinomialTrial extends Trial {
    private boolean result;
    public BinomialTrial() {}

    /**
     * Constructor
     * @param experimenter Experimenter ID
     * @param date Date the trial took place
     * @param result Pass or Fail result of the trial
     * @param location Location the trial took place
     * @param experimentID Experiment ID
     */
    public BinomialTrial(String experimenter, Date date, boolean result, Location location, String experimentID) {
        super(experimenter, date, location, experimentID);
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
