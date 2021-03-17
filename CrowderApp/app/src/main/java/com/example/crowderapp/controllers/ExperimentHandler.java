package com.example.crowderapp.controllers;

import android.location.Location;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;

import com.example.crowderapp.controllers.callbackInterfaces.allExperimentsCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.User;
import com.example.crowderapp.models.dao.ExperimentFSDAO;
import com.example.crowderapp.models.dao.TrialFSDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExperimentHandler {
    ArrayList<Experiment> experiments;

    private static ExperimentHandler instance;
    private ExperimentFSDAO experimentFSDAO;
    private Logger logger;

    private ExperimentHandler() {
        logger = Logger.getLogger(ExperimentHandler.class.getName());
    }

    public static ExperimentHandler getInstance() {
        if (instance == null)
            instance = new ExperimentHandler();

        return instance;
    }

    /**
     * creates an experiment
     */
    public Experiment createExperiment() {
        // TODO: have some code here to generate the id and what not
        // TODO: fill in parameters in the experiment.
        Experiment newExperiment = new Experiment();
        Task<String> task = experimentFSDAO.createExperiment(newExperiment);

        task.addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

               if (task.isSuccessful()) {
                   String experimentID = task.getResult();
                   newExperiment.setExperimentID(experimentID);
               } else {
                   Exception e = task.getException();
                   logger.throwing("Experiment Handler", "error in createExperiment unable to create experiment", e);
               }
            }
        });

        if (newExperiment.getExperimentID() == "") {
            //logger.throwing("Experiment Handler", "error in createExperiment no ID found");
            logger.log(Level.SEVERE, "Experiment Handler error in createExperiment no ID found");
        }

        return newExperiment;

    }

    /**
     * Unpublishes or deletes the experiment in the db
     * @param experimentID contains the experiment ID
     */
    public void unPublishExperiment(String experimentID) {
        // TODO: remove experiment from fire store
        Task<Experiment> task = experimentFSDAO.getExperiment(experimentID);

        task.addOnCompleteListener(new OnCompleteListener<Experiment>() {
            @Override
            public void onComplete(@NonNull Task<Experiment> task) {
                if (task.isSuccessful()) {
                    Experiment experimentToDelete = task.getResult();
                    experimentFSDAO.deleteExperiment(experimentToDelete);
                } else {
                    Exception e = task.getException();
                    logger.throwing("Experiment Handler", "error in unPublishExperiment obtaining Experiment", e);
                }
            }
        });
    }
    /**
     * creates and returns the task for all experiments the user is subscribed to.
     * @param userID contains the userID
     */
    public Task<List<Experiment>> getAllSubscribedExperiments(String userID) {

        Task<List<Experiment>> task = experimentFSDAO.getUserExperiments(userID);
        return task;

    }

    public void endExperiment(String experimentID) {
        // TODO: prevent owner and subscriber from adding a trial

    }


//    public String addTrial(String experimenterID, String experimentID, Date date, Location location) {
//        // TODO: check if Location needs to be a user-defined class
//        TrialFSDAO trialFSDAO;
//        Trial newTrial = new Trial(experimenterID, date, location, experimentID);
//        Task<String> taskAddTrial = trialFSDAO.addExperimentTrial(newTrial);
//        Task<Experiment> taskgetExperiment = experimentFSDAO.getExperiment(experimentID);
//
//        taskgetExperiment.addOnSuccessListener(new OnSuccessListener<Experiment>() {
//            @Override
//            public void onSuccess(Experiment experiment) {
//                trialFSDDAO
//            }
//        })
//
//        taskAddTrial.addOnSuccessListener(new OnSuccessListener<String>() {
//            @Override
//            public void onSuccess(String s) {
//                newTrial.setTrialId(taskAddTrial.getResult());
//            }
//        });
//
//        return newTrial.getTrialId();
//
//    }

    public List<Trial> getData(String experimentID) {
        // TODO: get trials from the experiment
        return null;
    }

    public List<Trial> getData(String experimentID, List<Integer> exclude) {
        // TODO: get trials from the experiment, excluding trials IDs listed in List<Integer> exclude
        return null;
    }

    public List<User> getAllExperimenters(String experimentID) {
        // TODO: get all participating experimenters of the given experiment
        return null;
    }

//    public ExperimentStats getStatistics(String experimentID) {
//        // TODO: get the corresponding ExperimentStats class for this experiment
//    }

    public void getAllExperiments(allExperimentsCallBack allReturnedExperiments) {
        Task<List<Experiment>> task = experimentFSDAO.getAllExperiments();

        task.addOnCompleteListener(new OnCompleteListener<List<Experiment>>() {
            @Override
            public void onComplete(@NonNull Task<List<Experiment>> task) {
                if (task.isSuccessful()) {
                    allReturnedExperiments.allExperiments(task.getResult());
                }
            }
        });
    }

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