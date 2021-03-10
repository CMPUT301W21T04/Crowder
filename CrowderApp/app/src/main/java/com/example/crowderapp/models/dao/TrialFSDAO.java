package com.example.crowderapp.models.dao;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Observer;

/*
    Tasks based on guide by Google Developers.
    https://developers.google.com/android/guides/tasks
    Licensed under Apache 2.0 License.
 */

public class TrialFSDAO extends TrialDAO {

    // Logging Tag
    final private String TAG = "TrialFSDAO";

    FirebaseFirestore db;

    // Collection of trials for the experiment.
    CollectionReference trialCollection;

    public TrialFSDAO(Experiment experiment) {
        super(experiment);

        db = FirebaseFirestore.getInstance();
        CollectionReference allExperimentTrials = db.collection("experiment");

        if (experimentID == null) {
            // Programmer messed up. Cannot add trials if no experiment in DB yet!
            throw new NullPointerException("Cannot add trials if experiment has null ID");
        }

        trialCollection = allExperimentTrials.document(experimentID)
                            .collection("trials");
    }

    /**
     * Gets all the trials for a given experiment.
     * @return
     */
    @Override
    public Task<List<Trial>> getExperimentTrials() {
        return trialCollection.get().continueWith(task -> {
            return task.getResult().toObjects(Trial.class);
        });
    }

    /**
     * Adds a trial for the experiment
     * @param trial
     * @return
     */
    @Override
    public Task<String> addExperimentTrial(Trial trial) {
        return trialCollection.add(trial).continueWith(task -> {
            DocumentReference doc = task.getResult();
            return doc.getId();
        });
    }
}
