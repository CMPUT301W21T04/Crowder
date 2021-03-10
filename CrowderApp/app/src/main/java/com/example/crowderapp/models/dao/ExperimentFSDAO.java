package com.example.crowderapp.models.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.crowderapp.models.Experiment;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
    Tasks based on guide by Google Developers.
    https://developers.google.com/android/guides/tasks
    Licensed under Apache 2.0 License.
 */

public class ExperimentFSDAO extends ExperimentDAO {

    // Logging Tag
    final private String TAG = "ExperimentFSDAO";

    FirebaseFirestore db;
    CollectionReference experimentCollection;

    public ExperimentFSDAO() {
        db = FirebaseFirestore.getInstance();
        experimentCollection = db.collection("experiment");
    }

    /**
     * Gets an experiment by its ID.
     *
     * @param experimentId The ID of the experiment to fetch.
     */
    @Override
    public Task<Experiment> getExperiment(String experimentId) {
        return experimentCollection.get().continueWithTask(task -> {

            TaskCompletionSource<Experiment> taskCompletionSource = new TaskCompletionSource<>();

            if (!task.isSuccessful()) {
                // Something bad happened... Fail the task.
                taskCompletionSource.setException(task.getException());
            } else {
                QuerySnapshot query = task.getResult();
                for (QueryDocumentSnapshot doc : query) {
                    if (doc.getId().equals(experimentId)) {
                        // This is the experiment we're looking for.
                        taskCompletionSource.setResult(doc.toObject(Experiment.class));
                        break;
                    }
                }
                // Set exception if no experiment found.
                taskCompletionSource.trySetException(new IOException("Experiment not found"));
            }

            return taskCompletionSource.getTask();
        });
    }

    /**
     * Gets a list of all experiments.
     */
    @Override
    public Task<List<Experiment>> getAllExperiments() {
        return experimentCollection.get().continueWithTask(task -> {
            TaskCompletionSource<List<Experiment>> taskCompletionSource = new TaskCompletionSource<>();

            if (!task.isSuccessful()) {
                // Something bad happened... Fail the task.
                taskCompletionSource.setException(task.getException());
            } else {
                QuerySnapshot query = task.getResult();
                taskCompletionSource.setResult(query.toObjects(Experiment.class));
            }

            return taskCompletionSource.getTask();
        });
    }

    /**
     * Creates a new experiment.
     *
     * @param exp The experiment
     * @return The string representing the experiment ID
     */
    @Override
    public Task<String> createExperiment(Experiment exp) {
        return experimentCollection.add(exp).continueWithTask(task -> {
            TaskCompletionSource<String> taskCompletionSource = new TaskCompletionSource<>();

            if (!task.isSuccessful()) {
                taskCompletionSource.setException(task.getException());
            } else {
                taskCompletionSource.setResult(task.getResult().getId());
            }

            return taskCompletionSource.getTask();
        });
    }

    /**
     * Removes an existing experiment.
     *
     * @param exp The experiment
     */
    @Override
    public void deleteExperiment(Experiment exp) {

        String id = exp.getExperimentID();

        experimentCollection.document(id).delete().addOnFailureListener(e -> {
            Log.e(TAG, "deleteExperiment: Failed to delete experiment.", e);
        });
    }

    /**
     * Updates data of an existing experiment.
     *
     * @param exp The experiment
     */
    @Override
    public void updateExperiment(Experiment exp) {
        String id = exp.getExperimentID();

        experimentCollection.document(id).set(exp).addOnFailureListener(e -> {
            Log.e(TAG, "updateExperiment: Failed to update experiment.");
        });
    }
}
