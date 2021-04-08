package com.example.crowderapp.models;

import com.google.firebase.firestore.DocumentId;

public class ScanObj {

    @DocumentId
    private int key;

    private String expID;
    private String value;

    public ScanObj(int key, String expID, String value) {
        this.key = key;
        this.expID = expID;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getexpID() {
        return expID;
    }

    public String getValue() {
        return value;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setexpID(String expName) {
        this.expID = expName;
    }

    public void setValue(String value) {
        this.value = value;
    }
}