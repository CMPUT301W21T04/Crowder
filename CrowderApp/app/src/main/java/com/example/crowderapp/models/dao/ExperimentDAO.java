package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.Experiment;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.reflect.Array;

/**
 * This is a base data access object for experiment data.
 */
public abstract class ExperimentDAO {
    /**
     * Gets an experiment by its ID.
     * @param listener Callback when the experiment is fetched.
     * @param experimentId The ID of the experiment to fetch.
     */
    abstract void getExperiment(OnSuccessListener<Experiment> listener, String experimentId);

    /**
     * Gets a list of all experiments.
     * @param listener
     */
    abstract void getAllExperiments(OnSuccessListener listener);

    /**
     * Creates a new experiment.
     * @param exp The experiment
     * @return The string representing the experiment ID
     */
    abstract String createExperiment(Experiment exp);

    /**
     * Removes an existing experiment.
     * @param exp The experiment
     */
    abstract void deleteExperiment(Experiment exp);

    /**
     * Updates data of an existing experiment.
     * @param exp The experiment
     */
    abstract void updateExperiment(Experiment exp);
}
