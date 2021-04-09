package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.google.android.gms.tasks.Task;

import java.util.List;

/**
 * Data access object for trial persistent data.
 */
public abstract class TrialDAO {

    protected String experimentID;

    // Binomial, Count, etc.
    protected String experimentType;

    /**
     * @param experiment The experiment whose trials to manipulate/view.
     */
    public TrialDAO(Experiment experiment) {
        experimentID = experiment.getExperimentID();
        experimentType = experiment.getExperimentType();
        if (experimentType == null) {
            throw new IllegalArgumentException("Invalid experiment type: " + experimentType);
        }
    }

    /**
     * Gets all the trials for a given experiment.
     * @return The task that resolves into the list of trials.
     */
    public abstract Task<List<Trial>> getExperimentTrials();

    /**
     * Get all the trials for a given experiment but have certain users'
     * trials be omitted from the returned trials.
     * @param excludedUsers List of User IDs to exclude.
     * @return The list of trials.
     */
    public abstract Task<List<Trial>> getExperimentTrialsUserFiltered(List<String> excludedUsers);

    /**
     * Adds a trial for the experiment
     * @param trial
     * @return The task that resolves into the trial ID.
     */
    public abstract Task<String> addExperimentTrial(Trial trial);
}
