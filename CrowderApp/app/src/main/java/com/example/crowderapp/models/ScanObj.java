package com.example.crowderapp.models;

import com.google.firebase.firestore.DocumentId;

/**
 * A ScanObj relates the key of the scanned object (barcode or qr code value)
 * to the experiment id and value that that code represents
 */
public class ScanObj {

    @DocumentId
    private String key;

    private String expID;
    private String value;

    /**
     * Constructor
     *
     */
    public ScanObj() {
    }

    public ScanObj(String key, String expID, String value) {
        this.key = key;
        this.expID = expID;
        this.value = value;
    }

    /**
     * Gets the key the of the code
     * @return
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets the experiment ID the key is related to
     * @return ExperimentID the code relates to
     */
    public String getexpID() {
        return expID;
    }

    /**
     * Gets the value the code is related to
     * @return Value the code relates to
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the key of the object
     * @param key Key the code relates to
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets the experiment ID that the code is related to
     * @param expName
     */
    public void setexpID(String expName) {
        this.expID = expName;
    }

    /**
     * Sets the value the code is related to
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}