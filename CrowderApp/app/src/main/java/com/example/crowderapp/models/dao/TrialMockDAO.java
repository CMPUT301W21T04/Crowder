package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrialMockDAO extends TrialDAO {

    List<Trial> trialList;

    // Use normal ints for mock testing
    int idCounter = 0;

    public TrialMockDAO(Experiment experiment) {
        super(experiment);
        trialList = new ArrayList<>();
    }

    /**
     * Gets all the trials for a given experiment.
     *
     * @return
     */
    @Override
    public Task<List<Trial>> getExperimentTrials() {
        TaskCompletionSource<List<Trial>> completionSource = new TaskCompletionSource<>();
        completionSource.setResult(trialList);
        return completionSource.getTask();
    }

    /**
     * Get all the trials for a given experiment but have certain users'
     * trials be omitted from the returned trials.
     *
     * @param excludedUsers List of User IDs to exclude.
     * @return The list of trials.
     */
    @Override
    public Task<List<Trial>> getExperimentTrialsUserFiltered(List<String> excludedUsers) {
        return null;
    }

    /**
     * Adds a trial for the experiment
     *
     * @param trial
     * @return
     */
    @Override
    public Task<String> addExperimentTrial(Trial trial) {
        TaskCompletionSource<String> completionSource = new TaskCompletionSource<>();
        trialList.add(trial);
        completionSource.setResult(String.valueOf(idCounter++));
        return completionSource.getTask();
    }
}
