package com.example.crowderapp.models;

import java.util.Date;

public class CounterTrial extends Trial {
    public CounterTrial() {
    }

    /**
     * Constructor
     * @param experimenter Experimenter ID
     * @param date Date the trial took place
     * @param location Location the trial took place
     * @param experimentID Experiment ID
     */
    public CounterTrial(String experimenter, Date date, Location location, String experimentID) {

        super(experimenter, date, location, experimentID);
    }
}
