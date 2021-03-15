package com.example.crowderapp.controllers;

import android.location.Location;

import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.dao.ExperimentFSDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExperimentHandler {
    ArrayList<Experiment> experiments;

    private static ExperimentHandler instance;
    private ExperimentFSDAO experimentFSDAO;

    private ExperimentHandler() {
        experimentFSDAO = new ExperimentFSDAO();
    }

    public static ExperimentHandler getInstance() {
        if (instance == null)
            instance = new ExperimentHandler();

        return instance;
    }

    public void createExperiment() {
        Experiment newExperiment = new Experiment();

        // TODO: have some code here to generate the id and what not

    }


    public void unPublishExperiment(String experimentID) {
        // TODO: remove experiment from fire store
    }


    public void endExperiment(String experimentID) {
        // TODO: prevent owner and subscriber from adding a trial
    }


    public void addTrial(String experimentID, Date date, Location location) {
        // TODO: check if Location needs to be a user-defined class
    }

//    public List<Trial> getData(String experimentID) {
//        // TODO: get trials from the experiment
//    }
//
//    public List<Trial> getData(String experimentID, List<Integer> exclude) {
//        // TODO: get trials from the experiment, excluding trials IDs listed in List<Integer> exclude
//    }
//
//    public List<User> getAllExperimenters(String experimentID) {
//        // TODO: get all participating experimenters of the given experiment
//    }
//
//    public ExperimentStats getStatistics(String experimentID) {
//        // TODO: get the corresponding ExperimentStats class for this experiment
//    }

    public void addQR(String experimentID) {
        // TODO: get the experiment object and call generateQR()
    }

    public List<Integer> getQR(String experimentID) {
        // Assuming QR code is of Integer type
        // TODO: get all QR codes associated with experiment

        return null;
    }

    // similar to the generateQR we will
    // need some api to read barcodes in
    public void registerBarcode() {
        // TODO: register a pre-existing barcode.

    }

    public List<Experiment> searchExperiment(List<String> filterStrings) {
        // TODO: get a list of experiments based on provided filter

        return null;
    }
}