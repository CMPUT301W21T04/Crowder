package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.Experiment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This is a base data access object for experiment data.
 */
public abstract class ExperimentDAO {
    /**
     * Gets an experiment by its ID.
     * @param experimentId The ID of the experiment to fetch.
     */
    public abstract Task<Experiment> getExperiment(String experimentId);

    /**
     * Gets a list of all experiments.
     */
    public abstract Task<ArrayList<Experiment>> getAllExperiments();

    /**
     * Creates a new experiment.
     * @param exp The experiment
     * @return The string representing the experiment ID
     */
    public abstract Task<String> createExperiment(Experiment exp);

    /**
     * Removes an existing experiment.
     * @param exp The experiment
     */
    public abstract void deleteExperiment(Experiment exp);

    /**
     * Updates data of an existing experiment.
     * @param exp The experiment
     */
    public abstract void updateExperiment(Experiment exp);
}
