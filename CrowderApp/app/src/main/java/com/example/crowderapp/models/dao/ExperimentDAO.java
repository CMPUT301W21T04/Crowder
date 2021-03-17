package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.Experiment;
import com.google.android.gms.tasks.Task;

import java.util.List;

/**
 * This is a base data access object for experiment data.
 */
public abstract class ExperimentDAO {
    /**
     * Gets an experiment by its ID.
     * @param experimentId The ID of the experiment to fetch.
     * @return The task that resolves into the experiment.
     */
    public abstract Task<Experiment> getExperiment(String experimentId);

    /**
     * @return The task that resolves into the list of experiments
     */
    public abstract Task<List<Experiment>> getAllExperiments();

    /**
     * Creates a new experiment.
     * @param exp The experiment
     * @return The task that resolves into the string representing the experiment ID
     */
    public abstract Task<String> createExperiment(Experiment exp);

    /**
     * Gets all the experiments that a user has subscribed to.
     * @param userId The user's ID
     * @return
     */
    public abstract Task<List<Experiment>> getUserExperiments(String userId);

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
