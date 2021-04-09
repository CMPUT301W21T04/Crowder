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
    private String region;

    @Exclude // Trials have their own DAO
    protected List<Trial> trials = new ArrayList<Trial>();
    private List<String> excludedUsers = new ArrayList<>();

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
        this.region = "";
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
    public Experiment(String experimentID, String name, String region, int minTrials, boolean isEnded, boolean isUnpublished, boolean isLocationRequired, String ownerID) {
        this.experimentID = experimentID;
        this.name = name;
        this.minTrials = minTrials;
        this.region = region;
        this.isEnded = isEnded;
        this.isUnpublished = isUnpublished;
        this.isLocationRequired = isLocationRequired;
        this.ownerID = ownerID;
    }

    /**
     * gets the experiment name
     * @return
     */
    public String getName() { return name; }

    /**
     * sets the experiment name
     * @param name : the experiment name
     */
    public void setName(String name) { this.name = name; }

    /**
     * gets the minimum number of trials
     * @return the minimum number of trials
     */
    public int getMinTrials() {
        return minTrials;
    }

    /**
     * sets the minimum number of trials
     * @param minTrials
     */
    public void setMinTrials(int minTrials) {
        this.minTrials = minTrials;
    }

    /**
     * gets the region where the experiment is conducted
     * @return
     */
    public String getRegion() { return this.region; }

    /**
     * sets the region where the experiment is conducted
     * @param region
     */
    public void setRegion(String region) { this.region = region; }

    /**
     * gets the experiment ID
     * @return the experiment ID
     */
    public String getExperimentID() {
        return experimentID;
    }

    /**
     * determines if the experiment has ended
     * @return boolean result if the experiment has ended
     */
    public boolean isEnded() {
        return isEnded;
    }

    /**
     * determines if the experiment is unpublished
     * @return boolean result if the experiment is unpublished
     */
    public boolean isUnpublished() {
        return isUnpublished;
    }

    /**
     * determines if the location is required
     * @return boolean result if the experiment location is required
     */
    public boolean isLocationRequired() {
        return isLocationRequired;
    }

    /**
     * grabs the owner experiment ID
     * @return the owner ID
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * sets the owner ID of the experiment
     * @param ownerID
     */
    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * sets the experiment ID
     * @param experimentID
     */
    public void setExperimentID(String experimentID) {
        this.experimentID = experimentID;
    }

    /**
     * sets the experiment to ended
     * @param ended
     */
    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    /**
     * sets the experiment to unpublished
     * @param unpublished
     */
    public void setUnpublished(boolean unpublished) {
        isUnpublished = unpublished;
    }

    /**
     * sets the location requirement
     * @param locationRequired
     */
    public void setLocationRequired(boolean locationRequired) {
        isLocationRequired = locationRequired;
    }

    /**
     * grabs all the users that are excluded from the expeirment
     * @return the list of excluded users
     */
    public List<String> getExcludedUsers() {
        return excludedUsers;
    }

    /**
     * sets all the excluded users
     * @param excludedUsers
     */
    public void setExcludedUsers(List<String> excludedUsers) {
        this.excludedUsers = excludedUsers;
    }

    /**
     * grabs all the trials in the experiment
     * @return the list of trials in the experiment
     */
    @Exclude
    public List<Trial> getTrials() {
        return trials;
    }

    /**
     * sets the trials in the experiment
     * @param trials
     */
    @Exclude
    public void setTrials(List<Trial> trials) {
        this.trials = trials;
    }

    /**
     * grabs the experiment type
     * @return the experiment type
     */
    public String getExperimentType() { return experimentType; }

    /**
     * sets the experiment type
     * @param experimentType
     */
    public void setExperimentType(String experimentType) { this.experimentType = experimentType; }

}
