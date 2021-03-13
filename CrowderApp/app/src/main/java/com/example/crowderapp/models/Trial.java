package com.example.crowderapp.models;

import com.google.firebase.firestore.DocumentId;

import java.util.Date;

public class Trial {

    @DocumentId // Mark this as document ID in firebase
    private String trialId;

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
