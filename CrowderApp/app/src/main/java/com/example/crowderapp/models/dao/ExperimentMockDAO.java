package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.Experiment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.ArrayList;

public class ExperimentMockDAO extends ExperimentDAO {

    private ArrayList<Experiment> expList;

    // Implement ID generation with simple integers.
    private int idCounter;

    public ExperimentMockDAO() {
        expList = new ArrayList<>();
        idCounter = 0;
    }


    /**
     * Gets an experiment by its ID.
     * @param experimentId The ID of the experiment to fetch.
     */
    @Override
    public Task<Experiment> getExperiment(String experimentId) {
        TaskCompletionSource<Experiment> taskSource = new TaskCompletionSource<>();

        for (Experiment exp : expList) {
            if (experimentId.equals(exp.getExperimentID())) {
                taskSource.setResult(exp);
                return taskSource.getTask();
            }
        }

        taskSource.setResult(null);
        return taskSource.getTask();
    }

    /**
     * Gets a list of all experiments.
     */
    @Override
    public Task<ArrayList<Experiment>> getAllExperiments() {
        TaskCompletionSource<ArrayList<Experiment>> taskSource = new TaskCompletionSource<>();
        taskSource.setResult(expList);
        return taskSource.getTask();
    }

    /**
     * Creates a new experiment.
     *
     * @param exp The experiment
     * @return The string representing the experiment ID
     */
    @Override
    public Task<String> createExperiment(Experiment exp) {
        expList.add(exp);

        TaskCompletionSource<String> taskSource = new TaskCompletionSource<>();
        taskSource.setResult(String.valueOf(idCounter++));
        return taskSource.getTask();
    }

    /**
     * Removes an existing experiment.
     *
     * @param exp The experiment
     */
    @Override
    public void deleteExperiment(Experiment exp) {
        expList.remove(exp);
    }

    /**
     * Updates data of an existing experiment.
     *
     * @param exp The experiment
     */
    @Override
    public void updateExperiment(Experiment exp) {
        for (int i = 0; i < expList.size(); i++) {
            if (exp == expList.get(i)) {
                expList.set(i, exp);
            }
        }
    }
}
