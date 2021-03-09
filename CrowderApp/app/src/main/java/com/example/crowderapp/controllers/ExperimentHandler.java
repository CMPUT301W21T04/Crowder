package com.example.crowderapp.controllers;

import com.example.crowderapp.models.Experiment;

public class ExperimentHandler {

    protected ExperimentHandler instance;

    public ExperimentHandler() {

        // generates an experimentHandler
        instance = this;

    }

    public ExperimentHandler getInstance() {
        return instance;
    }

    public void createExperiment() {
        Experiment newExperiment = new Experiment();

        // have some code here to generate the id and what not

    }

    public void unPublishExperiment() {

    }



}
