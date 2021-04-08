package com.example.crowderapp.controllers;

import android.content.Context;

import com.example.crowderapp.controllers.callbackInterfaces.getExperimentCallBack;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;

public class ScanHandler {

    private ExperimentHandler handler = new ExperimentHandler();
    UserHandler userHandler;


    public ScanHandler() {
        userHandler = new UserHandler(getActivity().getSharedPreferences(
                UserHandler.USER_DATA_KEY, Context.MODE_PRIVATE));
    }

    public void execute(String expID, String value) {
        handler.getExperiment(expID, new getExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {
                Experiment exp = experiment;
                String type = exp.getExperimentType();
                switch(type) {
                    case "Binomial":
                        Trial trial = new BinomialTrial()
//                        handler.addTrial();
                        break;
                    case "Count":
                        // Do something
                        break;
                    case "Non-Negative Integer":
                        // Do something
                        break;
                    case "Measurement":
                        //Do something
                        break;
                }
            }
        });
    }
}
