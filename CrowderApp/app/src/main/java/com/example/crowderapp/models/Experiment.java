package com.example.crowderapp.models;

import java.util.ArrayList;
import java.util.List;

public class Experiment {

    private int experimentID;
    private int minTrials;
    private boolean isEnded;
    private boolean isUnpublished;
    private boolean isLocationRequired;
    private int ownerID;
    protected List<Trial> trials = new ArrayList<Trial>();

    public Experiment(int experimentID, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, int ownerID) {
        this.experimentID = experimentID;
        this.minTrials = minTrials;
        this.isEnded = isEnded;
        this.isUnpublished = isUnpublished;
        this.isLocationRequired = isLocationRequired;
        this.ownerID = ownerID;
    }

    public int getMinTrials() {
        return minTrials;
    }

    public void setMinTrials(int minTrials) {
        this.minTrials = minTrials;
    }

    public int getExperimentID() {
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

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public void setExperimentID(int experimentID) {
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

    // this requires attention will need
    // to use some api to generate QR codes
    public void generateQR() {

    }

    // similar to the generateQR we will
    // need some api to read barcodes in
    public void registerBarcode(Long barcode) {

    }
}
