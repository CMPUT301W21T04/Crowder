package com.example.crowderapp.controllers.callbackInterfaces;

import com.example.crowderapp.models.Experiment;

import java.util.List;

public interface searchExperimentCallBack {
    public void callBackResult(List<Experiment> experimentList);
}
