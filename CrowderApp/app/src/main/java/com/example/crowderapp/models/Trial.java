package com.example.crowderapp.models;


import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class Trial {

    @DocumentId // Mark this as document ID in firebase
    private String trialId;

    private String experimenter;
    private Date date;
    private String experimentID;

    private Location location;

    public Trial() {
    }

    public Trial(String experimenter, Date date, Location location, String experimentID) {
        this.experimenter = experimenter;
        this.date = date;
        this.location = location;
        this.experimentID = experimentID;
    }

    public String getExperimenter() {
        return experimenter;
    }

    public Date getDate() {
        return date;
    }

    public String getExperimentID() {
        return experimentID;
    }

    public void setExperimentID(String experimentID) {
        this.experimentID = experimentID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getTrialId() {
        return trialId;
    }

    public void setTrialId(String trialId) {
        this.trialId = trialId;
    }

}
