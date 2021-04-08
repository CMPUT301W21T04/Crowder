package com.example.crowderapp.models;


import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.Calendar;
import java.util.Date;

/**
 * Super class for all trials
 *
 * Trials are added to an experiment
 */
public class Trial implements Comparable<Trial> {

    @DocumentId // Mark this as document ID in firebase
    private String trialId;

    private String experimenter;
    private Date date;
    private String experimentID;

    private Location location;

    public Trial() {
    }

    /**
     * Contstructor
     * @param experimenter unique id of experimenter that took the trial
     * @param date the date the trial was taken
     * @param location the location the trial was taken
     * @param experimentID the unique id of the experiment to add trial to
     */
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

    @Override
    public int compareTo(Trial other) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(getDate());
        cal2.setTime(other.getDate());
        if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
            if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                return 0;
            } else if (cal1.get(Calendar.DAY_OF_YEAR) < cal2.get(Calendar.DAY_OF_YEAR)) {
                return -1;
            } else {
                return 1;
            }
        } else if (cal1.get(Calendar.YEAR) < cal2.get(Calendar.YEAR)) {
            return -1;
        } else {
            return 1;
        }
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
