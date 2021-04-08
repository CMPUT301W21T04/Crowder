package com.example.crowderapp.controllers;

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
import com.example.crowderapp.models.Location;
import com.example.crowderapp.models.Search;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.dao.ExperimentDAO;
import com.example.crowderapp.models.dao.ExperimentFSDAO;
import com.example.crowderapp.models.dao.TrialFSDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExperimentHandler {
    private ExperimentDAO experimentDAO;
    private Logger logger;

    public ExperimentHandler() {
        experimentDAO = new ExperimentFSDAO();
        logger = Logger.getLogger(ExperimentHandler.class.getName());
    }

    public ExperimentHandler(ExperimentDAO dao) {
        experimentDAO = dao;
        logger = Logger.getLogger(ExperimentHandler.class.getName());
    }

    /**
     * creates an experiment
     * @param experimentName experiment name
     * @param isLocationRequired is the location required flag
     * @param minTrials minimum trials count
     * @param experimentType the type of experiment
     * @param ownerID the ownerID string
     * @param callBack the callback interface for the async call
     */
    public void createExperiment(String experimentName, boolean isLocationRequired, String region,
                                 int minTrials, String experimentType, String ownerID,
                                 createExperimentCallBack callBack) {
        // TODO: have some code here to generate the id and what not
        // TODO: fill in parameters in the experiment.
        Experiment newExperiment = new Experiment();
        newExperiment.setName(experimentName);
        newExperiment.setRegion(region);
        newExperiment.setLocationRequired(isLocationRequired);
        newExperiment.setMinTrials(minTrials);
        newExperiment.setExperimentType(experimentType);
        newExperiment.setOwnerID(ownerID);
        Task<String> task = experimentDAO.createExperiment(newExperiment);

        task.addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    newExperiment.setExperimentID(task.getResult());
                    callBack.callBackResult(newExperiment);
                }
            }
        });
    }

    /**
     * Unpublishes or deletes the experiment in the db
     * @param experimentID contains the experiment ID
     * @param callback
     */
    public void unPublishExperiment(String experimentID, unPublishExperimentCallBack callback) {
        // TODO: remove experiment from fire store
        Task<Experiment> task = experimentDAO.getExperiment(experimentID);

        task.addOnCompleteListener(new OnCompleteListener<Experiment>() {
            @Override
            public void onComplete(@NonNull Task<Experiment> task) {
                if (task.isSuccessful()) {
                    Experiment experimentToDelete = task.getResult();
                    experimentDAO.deleteExperiment(experimentToDelete);
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
     * @param callback the callback function that is called when the async call finish
     */
    public void getAllSubscribedExperiments(String userID, getAllSubscribedExperimentsCallBack callback) {

        Task<List<Experiment>> task = experimentDAO.getUserExperiments(userID);

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

    /**
     * ends experiment then passes the updated experiment to be updated in the db
     * @param experiment the experiment to be ended
     */
    public void endExperiment(Experiment experiment) {
        // TODO: prevent owner and subscriber from adding a trial

        experiment.setEnded(true);
        experimentDAO.updateExperiment(experiment);

    }

    /**
     * This grabs the experiment in the database given the experiment ID
     * @param experimentID the experiment ID that is to be grabbed
     * @param callback the callback function when the async call is done
     */
    public void getExperiment(String experimentID, getExperimentCallBack callback){
        Task<Experiment> task = experimentDAO.getExperiment(experimentID);

        task.addOnCompleteListener(new OnCompleteListener<Experiment>() {
            @Override
            public void onComplete(@NonNull Task<Experiment> task) {
                if (task.isSuccessful()) {
                    // Fill experiment with the trials.
                    refreshExperimentTrials(task.getResult(), completeExperiment -> callback.callBackResult(completeExperiment));
                } else {
                    logger.log(Level.SEVERE, String.format("Failed to get experiment %s in experiment handler.", experimentID));
                }
            }
        });

    }

    /**
     * Populates an experiment with updated trials.
     * @param exp The experiment
     */
    public void refreshExperimentTrials(Experiment exp, getExperimentCallBack cb) {
         new TrialFSDAO(exp).getExperimentTrials().addOnSuccessListener(trials -> {
            exp.setTrials(trials);
            cb.callBackResult(exp);
        }).addOnFailureListener(command -> {
            logger.log(Level.SEVERE, String.format("Failed to get experiment trials in experiments handler."));
        });
    }

    /**
     * This adds a trial to an experiment
     * @param trial the trial to be added
     * @param callBack the callback function when the async call is finished
     */
    public void addTrial(Trial trial, addTrialCallBack callBack) {
        // TODO: check if Location needs to be a user-defined class

        getExperiment(trial.getExperimentID(), new getExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {

                TrialFSDAO trialFSDAO;
                trialFSDAO = new TrialFSDAO(experiment);
                Task<String> taskAddTrial = trialFSDAO.addExperimentTrial(trial);

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

    /**
     * updates the experiments
     * @param experiment the experiment to be updated
     */
    public void updateExperiment(Experiment experiment) {

        experimentDAO.updateExperiment(experiment);

    }

//    public ExperimentStats getStatistics(String experimentID) {
//        // TODO: get the corresponding ExperimentStats class for this experiment
//    }

    /**
     * grabs all the experiments
     * @param callback the callback function when all experiments are grabbed
     */
    public void getAllExperiments(allExperimentsCallBack callback) {
        Task<List<Experiment>> task = experimentDAO.getAllExperiments();

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

    public ArrayList<LatLng> getLatLongExperiment(Experiment experiment) {
        List<Trial> trialList = experiment.getTrials();
        ArrayList<LatLng> latLngs = new ArrayList<LatLng>();

        for (Trial trial : trialList) {
            latLngs.add(new LatLng(trial.getLocation().getLatitude(), trial.getLocation().getLongitude()));
        }

        return latLngs;
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

    /**
     * Searches all experiments for a particular string in any field in the experiment object
     * @param filterStrings the strings to be searched
     * @param callback the callback function when the async call finishes
     */
    public void searchExperiment(List<String> filterStrings, searchExperimentCallBack callback) {
        // TODO: get a list of experiments based on provided filter

        Search search = new Search();

        getAllExperiments(new allExperimentsCallBack() {
            @Override
            public void callBackResult(List<Experiment> experimentList) {
                List<Experiment> filteredExperiments = search.searchExperiments((ArrayList<String>) filterStrings, experimentList);
                callback.callBackResult(filteredExperiments);
            }
        });

    }
}