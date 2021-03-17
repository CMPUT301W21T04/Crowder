package com.example.crowderapp.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Experiment {

    @DocumentId // Designate this as document ID in Firestore.
    private String experimentID;



    private String name;
    private int minTrials;
    private boolean isEnded;
    private boolean isUnpublished;
    private boolean isLocationRequired;
    private String ownerID;

    public Experiment() {
        this.experimentID = null;
        this.minTrials = 0;
        this.isEnded = false;
        this.isUnpublished = false;
        this.isLocationRequired = false;
        this.ownerID = "";
    }

    public Experiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        this.experimentID = experimentID;
        this.name = name;
        this.minTrials = minTrials;
        this.isEnded = isEnded;
        this.isUnpublished = isUnpublished;
        this.isLocationRequired = isLocationRequired;
        this.ownerID = ownerID;
    }

    public abstract List<? extends Trial> getTrials();

    public String getName() { return name; }

    public int getMinTrials() {
        return minTrials;
    }

    public void setMinTrials(int minTrials) {
        this.minTrials = minTrials;
    }

    public String getExperimentID() {
        return experimentID;
    }

    public boolean isEnded() {
        return isEnded;
    }

    public boolean isUnpublished() {
        return isUnpublished;
    }

    public boolean isLocationRequired() {
        return isLocationRequired;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public void setExperimentID(String experimentID) {
        this.experimentID = experimentID;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public void setUnpublished(boolean unpublished) {
        isUnpublished = unpublished;
    }

    public void setLocationRequired(boolean locationRequired) {
        isLocationRequired = locationRequired;
    }

    public abstract ExperimentStats getStats();

    // this requires attention will need
    // to use some api to generate QR codes
    public void generateQR() {

    }

    // similar to the generateQR we will
    // need some api to read barcodes in
    public void registerBarcode(Long barcode) {

    }
}
