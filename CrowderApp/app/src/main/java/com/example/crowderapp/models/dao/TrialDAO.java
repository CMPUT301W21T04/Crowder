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

    /**
     * @param experiment The experiment whose trials to manipulate/view.
     */
    public TrialDAO(Experiment experiment) {
        experimentID = experiment.getExperimentID();
    }

    /**
     * Gets all the trials for a given experiment.
     * @return
     */
    public abstract Task<List<Trial>> getExperimentTrials();

    /**
     * Adds a trial for the experiment
     * @param trial
     * @return
     */
    public abstract Task<String> addExperimentTrial(Trial trial);
}
