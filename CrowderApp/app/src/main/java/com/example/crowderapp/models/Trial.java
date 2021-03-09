package com.example.crowderapp.models;

import java.util.Date;

public class Trial {

    private String experimenter;
    private Date date;

    public Trial(String experimenter, Date date) {
        this.experimenter = experimenter;
        this.date = date;
    }

    public String getExperimenter() {
        return experimenter;
    }

    public Date getDate() {
        return date;
    }
}
