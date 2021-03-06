package com.example.crowderapp.controllers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.crowderapp.controllers.callbackInterfaces.LocationCallback;
import com.example.crowderapp.controllers.callbackInterfaces.ScanObjectCallback;
import com.example.crowderapp.controllers.callbackInterfaces.addTrialCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentCallBack;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.CounterTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Location;
import com.example.crowderapp.models.MeasurementTrial;
import com.example.crowderapp.models.ScanObj;
import com.example.crowderapp.models.TallyTrial;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.User;
import com.example.crowderapp.views.LocationPopupFragment;

import java.util.Date;

public class ScanHandler {

    private Experiment experiment;
    Trial trial;
    private ScanObj scanObj;
    private User user;
    private Location location = new Location();

    private Activity activity;
    private ExperimentHandler handler = new ExperimentHandler();
    private ScanObjHandler scanObjHandler;
    private LocationHandler locationHandler;

    public ScanHandler(Activity activity, Experiment experiment, User user) {
        this.experiment = experiment;
        this.user = user;
        this.activity = activity;

        if(experiment.isLocationRequired()) {
            locationHandler = new LocationHandler(activity.getApplicationContext());
            if(locationHandler.hasGPSPermissions()) {
                locationHandler.getCurrentLocation(new LocationCallback() {
                    @Override
                    public void callbackResult(Location loc) {
                        location = loc;
                    }
                });
            }
        }


        scanObjHandler = new ScanObjHandler(this.experiment.getExperimentID());
    }

    public void execute(String key) {
        scanObjHandler.getScanObj(key, new ScanObjectCallback() {
            @Override
            public void callback(ScanObj o) {
                scanObj = o;

                if(o.getexpID().equals("NOT_VALID") || o.getValue().equals("NOT_VALID")) {
                    Toast.makeText(activity.getApplicationContext(), "Invalid QR for this experiment", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Not subscribed to the experiment, return
                if(!user.getSubscribedExperiments().contains(scanObj.getexpID())) {
                    Toast.makeText(activity.getApplicationContext(), "Not allowed to add to this experiment", Toast.LENGTH_SHORT).show();
                    return;
                }

                switch(experiment.getExperimentType()) {
                    case "Binomial":
                        if(scanObj.getValue().equals("Pass")) {
                            trial = new BinomialTrial(user.getUid(), new Date(), true, location, experiment.getExperimentID());
                        }
                        else if(scanObj.getValue().equals("Fail")) {
                            trial = new BinomialTrial(user.getUid(), new Date(), false, location, experiment.getExperimentID());
                        }
                        handler.addTrial(trial, new addTrialCallBack() {
                            @Override
                            public void callBackResult(String trialID) {
                                Log.d(trialID, "Trial ID returned");
                                Toast.makeText(activity.getApplicationContext(), "Trial value " + scanObj.getValue() + " added!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Count":
                        trial = new CounterTrial(user.getUid(), new Date(), location, experiment.getExperimentID());
                        handler.addTrial(trial, new addTrialCallBack() {
                            @Override
                            public void callBackResult(String trialID) {
                                Log.d(trialID, "Trial ID returned");
                                Toast.makeText(activity.getApplicationContext(), "Count trial added!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Non-Negative Integer":
                        trial = new TallyTrial(user.getUid(), new Date(), Integer.valueOf(scanObj.getValue()), location, experiment.getExperimentID());
                        handler.addTrial(trial, new addTrialCallBack() {
                            @Override
                            public void callBackResult(String trialID) {
                                Log.d(trialID, "Trial ID returned");
                                Toast.makeText(activity.getApplicationContext(), "Trial value " + scanObj.getValue() + " added!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case "Measurement":
                        trial = new MeasurementTrial(user.getUid(), new Date(), Double.parseDouble(scanObj.getValue()), location, experiment.getExperimentID());
                        handler.addTrial(trial, new addTrialCallBack() {
                            @Override
                            public void callBackResult(String trialID) {
                                Log.d(trialID, "Trial ID returned");
                                Toast.makeText(activity.getApplicationContext(), "Trial value " + scanObj.getValue() + " added!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
            }
        });
    }
}


