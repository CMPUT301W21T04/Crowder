package com.example.crowderapp.models;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // Regex Expressions

    // https://stackoverflow.com/questions/16699007/regular-expression-to-match-standard-10-digit-phone-number
    // By Ravi K Thapliyal, https://stackoverflow.com/users/1237040/ravi-k-thapliyal
    // Licensed under CC BY-SA 4.0
    private static final String phoneRegex = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";

    // https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
    // By bortzmeyer, https://stackoverflow.com/users/15625
    // Licensed under CC BY-SA 4.0
    private static final String emailRegex =
            "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08" +
            "\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:" +
            "(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])" +
            "|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


    /**
     * Empty Constructor to initialize a User
     */
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

    /**
     * Validates the given string is a phone number.
     * @param phone Phone number to validate.
     * @return True if valid phone number.
     */
    static public boolean validPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * Validates the given string is a valid email.
     * @param email The email to validate.
     * @return True if valid email.
     */
    static public boolean validEmail(String email) {
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
