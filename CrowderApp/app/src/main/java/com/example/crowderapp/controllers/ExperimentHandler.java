package com.example.crowderapp.controllers;

import android.location.Location;

import androidx.annotation.NonNull;

import com.example.crowderapp.controllers.callbackInterfaces.addQRCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.addTrialCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.allExperimentsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.createExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.endExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getAllExperimentersCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getAllSubscribedExperimentsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getQRCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getTrialsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.registerBarcodeCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.searchExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.User;
import com.example.crowderapp.models.dao.ExperimentFSDAO;
import com.example.crowderapp.models.dao.TrialFSDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

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
        experimentFSDAO = new ExperimentFSDAO();
        logger = Logger.getLogger(ExperimentHandler.class.getName());
    }

    public static ExperimentHandler getInstance() {
        if (instance == null)
            instance = new ExperimentHandler();

        return instance;
    }

    /**
     * creates an experiment
     * @param experimentName experiment name
     * @param isLocationRequired is the location required flag
     * @param minTrials minimum trials count
     * @param experimentType the type of experiment
     * @param callBack the callback interface for the async call
     */
    public void createExperiment(String experimentName, boolean isLocationRequired,
                                 int minTrials, String experimentType,
                                 createExperimentCallBack callBack) {
        // TODO: have some code here to generate the id and what not
        // TODO: fill in parameters in the experiment.
        Experiment newExperiment = new Experiment();
        newExperiment.setName(experimentName);
        newExperiment.setLocationRequired(isLocationRequired);
        newExperiment.setMinTrials(minTrials);
        newExperiment.setExperimentType(experimentType);
        Task<String> task = experimentFSDAO.createExperiment(newExperiment);

        task.addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    callBack.callBackResult(newExperiment);
                }
            }
        });
    }

    /**
     * Unpublishes or deletes the experiment in the db
     * @param experimentID contains the experiment ID
     */
    public void unPublishExperiment(String experimentID, unPublishExperimentCallBack callback) {
        // TODO: remove experiment from fire store
        Task<Experiment> task = experimentFSDAO.getExperiment(experimentID);

        task.addOnCompleteListener(new OnCompleteListener<Experiment>() {
            @Override
            public void onComplete(@NonNull Task<Experiment> task) {
                if (task.isSuccessful()) {
                    Experiment experimentToDelete = task.getResult();
                    experimentFSDAO.deleteExperiment(experimentToDelete);
                    callback.callBackResult();
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
    public void getAllSubscribedExperiments(String userID, getAllSubscribedExperimentsCallBack callback) {

        Task<List<Experiment>> task = experimentFSDAO.getUserExperiments(userID);

        task.addOnCompleteListener(new OnCompleteListener<List<Experiment>>() {
            @Override
            public void onComplete(@NonNull Task<List<Experiment>> task) {
                if (task.isSuccessful()){
                    callback.callBackResult(task.getResult());
                } else {
                    logger.log(Level.SEVERE, "Error in get all subscribed experiments in handler");
                }
            }
        });

    }

    public void endExperiment(String experimentID, endExperimentCallBack callback) {
        // TODO: prevent owner and subscriber from adding a trial
    }


    public void getExperiment(String experimentID, getExperimentCallBack callback){
        Task<Experiment> task = experimentFSDAO.getExperiment(experimentID);

        task.addOnCompleteListener(new OnCompleteListener<Experiment>() {
            @Override
            public void onComplete(@NonNull Task<Experiment> task) {
                if (task.isSuccessful()) {
                    callback.callBackResult(task.getResult());
                } else {
                    logger.log(Level.SEVERE, "Error in get experiment in handler");
                }
            }
        });

    }

    public void addTrial(String experimenterID, String experimentID, Date date, Location location, addTrialCallBack callBack) {
        // TODO: check if Location needs to be a user-defined class

        getExperiment(experimentID, new getExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {

                TrialFSDAO trialFSDAO;
                Trial newTrial = new Trial(experimenterID, date, location, experimentID);
                trialFSDAO = new TrialFSDAO(experiment);
                Task<String> taskAddTrial = trialFSDAO.addExperimentTrial(newTrial);

                taskAddTrial.addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            callBack.callBackResult(task.getResult());
                        } else {
                            logger.log(Level.SEVERE, "Error in add trial in handler");
                        }
                    }
                });
            }
        });

    }

    public void getTrials(String experimentID, getTrialsCallBack callback) {
        // TODO: get trials from the experiment
    }

    public void getTrials(String experimentID, List<Integer> exclude, getTrialsCallBack callback) {
        // TODO: get trials from the experiment, excluding trials IDs listed in List<Integer> exclude

    }

    public void getAllExperimenters(String experimentID, getAllExperimentersCallBack callback) {
        // TODO: get all participating experimenters of the given experiment
    }

//    public ExperimentStats getStatistics(String experimentID) {
//        // TODO: get the corresponding ExperimentStats class for this experiment
//    }

    public void getAllExperiments(allExperimentsCallBack callback) {
        Task<List<Experiment>> task = experimentFSDAO.getAllExperiments();

        task.addOnCompleteListener(new OnCompleteListener<List<Experiment>>() {
            @Override
            public void onComplete(@NonNull Task<List<Experiment>> task) {
                if (task.isSuccessful()) {
                    callback.callBackResult(task.getResult());
                } else {
                    logger.log(Level.SEVERE, "Error in get all experiments in handler");
                }
            }
        });
    }

    public void addQR(String experimentID, addQRCallBack callback) {
        // TODO: get the experiment object and call generateQR()
    }

    public void getQR(String experimentID, getQRCallBack callback) {
        // Assuming QR code is of Integer type
        // TODO: get all QR codes associated with experiment
    }

    // similar to the generateQR we will
    // need some api to read barcodes in
    public void registerBarcode(registerBarcodeCallBack callback) {
        // TODO: register a pre-existing barcode.

    }

    public void searchExperiment(List<String> filterStrings, searchExperimentCallBack callback) {
        // TODO: get a list of experiments based on provided filter
    }
}