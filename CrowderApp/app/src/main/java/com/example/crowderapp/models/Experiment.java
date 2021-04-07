package com.example.crowderapp.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Holds all experiment attributes
 */
public class Experiment implements Serializable {

    @DocumentId // Designate this as document ID in Firestore.
    private String experimentID;

    private String name;
    private String experimentType;
    private int minTrials;
    private boolean isEnded;
    private boolean isUnpublished;
    private boolean isLocationRequired;
    private String ownerID;

    @Exclude // Trials have their own DAO
    protected List<Trial> trials = new ArrayList<Trial>();

    /**
     * Empty constructor to generate an empty experiment
     */
    public Experiment() {
        this.experimentID = null;
        this.minTrials = 0;
        this.isEnded = false;
        this.isUnpublished = false;
        this.isLocationRequired = false;
        this.ownerID = "";
        this.experimentType = "";
    }

    /**
     * Constructor
     * @param name name of experiment
     */
    public Experiment(String name) {
        this.name = name;
    }

    /**
     * Constructor
     * @param experimentID Unique identifier for an experiment
     * @param name the name of the experiment
     * @param minTrials the minimum number of trial per publish
     * @param isEnded boolean whether the experiment is ended or not
     * @param isUnpublished boolean whether the experiment is unpublished
     * @param isLocationRequired boolean whether the location is required for teh experiment
     * @param ownerID unique identifier for the owner of an experiment
     */
    public Experiment(String experimentID, String name, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        this.experimentID = experimentID;
        this.name = name;
        this.minTrials = minTrials;
        this.isEnded = isEnded;
        this.isUnpublished = isUnpublished;
        this.isLocationRequired = isLocationRequired;
        this.ownerID = ownerID;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

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

    public String getExperimentType() { return experimentType; }

    public void setExperimentType(String experimentType) { this.experimentType = experimentType; }

    public List<Trial> getTrials() {
        return trials;
    }

    public void setTrials(List<Trial> trials) {
        this.trials = trials;
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
