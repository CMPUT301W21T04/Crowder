package com.example.crowderapp.controllers;

import android.location.Location;

import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.dao.ExperimentFSDAO;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class ExperimentHandler {
    ArrayList<Experiment> experiments;

    private static ExperimentHandler instance;
    private ExperimentFSDAO experimentFSDAO;
    private Logger logger;

    private ExperimentHandler() {
        experimentFSDAO = new ExperimentFSDAO();
        logger = Logger.getLogger(ExperimentHandler.class.getName());
    }

    public static ExperimentHandler getInstance() {
        if (instance == null)
            instance = new ExperimentHandler();

        return instance;
    }

    public void createExperiment() {
        // TODO: have some code here to generate the id and what not
        // TODO: fill in parameters in the experiment.
        Experiment newExperiment = new Experiment();
        Task<String> task = experimentFSDAO.createExperiment(newExperiment);

        try{
            String experimentID = Tasks.await(task);
            newExperiment.setExperimentID(experimentID);
        } catch(Exception e) {
            logger.throwing("Experiment Handler", "error in unPublishExperiment obtaining Experiment", e);
        }

    }

    public void unPublishExperiment(String experimentID) {
        // TODO: remove experiment from fire store

        //create an async task that listens
        Task<Experiment> task = experimentFSDAO.getExperiment(experimentID);

        try{
            Experiment experiment = Tasks.await(task);
            experimentFSDAO.deleteExperiment(experiment);
        } catch(Exception e) {
            logger.throwing("Experiment Handler", "error in unPublishExperiment obtaining Experiment", e);
        }

    }

    public void unPublishExperiment(Experiment experiment) {

        experiment.setUnpublished(true);
        experimentFSDAO.updateExperiment(experiment);
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