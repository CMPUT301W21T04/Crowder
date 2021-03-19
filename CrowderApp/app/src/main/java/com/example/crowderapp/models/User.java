package com.example.crowderapp.models;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.perfmark.Link;

/**
 * Represents the current user using the app, or other users who have the app.
 */
public class User implements Serializable {

    @DocumentId // Tell Firebase to use this as UUID
    private String uid;

    // Personal information
    private String name;
    private String email;
    private String phone;

    // Experiment Information
    private List<String> subscribedExperiments;

    public User() {
        subscribedExperiments = new LinkedList<>();

        name = "";
        email = "";
        phone = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getSubscribedExperiments() {
        return subscribedExperiments;
    }

    public void setSubscribedExperiments(List<String> subscribedExperiments) {
        this.subscribedExperiments = subscribedExperiments;
    }
}
