package com.example.crowderapp.models;

import com.google.firebase.firestore.DocumentId;

public class ScanObj {

    @DocumentId
    private String key;

    private String expID;
    private String value;

    public ScanObj() {
    }

    public ScanObj(String key, String expID, String value) {
        this.key = key;
        this.expID = expID;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getexpID() {
        return expID;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setexpID(String expName) {
        this.expID = expName;
    }

    public void setValue(String value) {
        this.value = value;
    }
}