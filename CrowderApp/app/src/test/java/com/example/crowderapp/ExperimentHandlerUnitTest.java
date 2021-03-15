package com.example.crowderapp;

import com.example.crowderapp.controllers.ExperimentHandler;

import org.junit.Test;
import org.junit.Assert;


public class ExperimentHandlerUnitTest {

    /**
     * Check that an instance is returned
     */
    @Test
    public void testInstance() {
        ExperimentHandler instance = ExperimentHandler.getInstance();
        Assert.assertNotNull(instance);
    }

    /**
     * Check that an experiment can be created
     */
    @Test
    public void testCreateExperiment() {
        ExperimentHandler expHandler = ExperimentHandler.getInstance();

        Assert.assertTrue(expHandler.getExperiments().isEmpty());

        // check if experiment is in the local cache

        // check if experiment is in firestore
    }



}
