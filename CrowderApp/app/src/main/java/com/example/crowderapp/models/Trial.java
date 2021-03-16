package com.example.crowderapp.models;

import com.google.firebase.firestore.DocumentId;

import java.util.Calendar;
import java.util.Date;

public class Trial implements Comparable<Trial> {

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
}
