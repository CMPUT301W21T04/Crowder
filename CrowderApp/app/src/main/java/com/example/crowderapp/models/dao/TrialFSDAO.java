package com.example.crowderapp.models.dao;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.CounterTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.MeasurementTrial;
import com.example.crowderapp.models.TallyTrial;
import com.example.crowderapp.models.Trial;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observer;

import io.perfmark.Link;

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

    /**
     * Construct the proper trial based on the document type.
     */
    public Trial getProperTrial(DocumentSnapshot doc) {
        if(experimentType.equals("Non-Negative Integer")) {
            return doc.toObject(TallyTrial.class);
        }
        else if (experimentType.equals("Measurement")) {
            return doc.toObject(MeasurementTrial.class);
        }
        else if (experimentType.equals("Binomial")) {
            return doc.toObject(BinomialTrial.class);
        }
        else if (experimentType.equals("Count")) {
            //Must be counter trial
            return doc.toObject(CounterTrial.class);
        }
        else {
            throw new IllegalArgumentException("Unknown Experiment type: " + experimentType);
        }
    }

    public TrialFSDAO(Experiment experiment, FirebaseFirestore db) {
        super(experiment);
        this.db = db;
        CollectionReference allExperimentTrials = db.collection("experiment");

        if (experimentID == null) {
            // Programmer messed up. Cannot add trials if no experiment in DB yet!
            throw new NullPointerException("Cannot add trials if experiment has null ID");
        }

        trialCollection = allExperimentTrials.document(experimentID)
                .collection("trials");
    }

    public TrialFSDAO(Experiment experiment) {
        this(experiment, FirebaseFirestore.getInstance());
    }

    /**
     * Gets all the trials for a given experiment.
     * @return The Task for list of trials.
     *         Task will return null if something goes wrong.
     */
    @Override
    public Task<List<Trial>> getExperimentTrials() {
        return trialCollection.get().continueWith(task -> {
            List<Trial> trialList = new LinkedList<>();
            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                Trial trial = getProperTrial(doc);
                trialList.add(trial);
            }
            return trialList;
        }).addOnFailureListener(e -> {
            Log.e(TAG, "getExperimentTrials: Failed to get all trials for experiment " + experimentID, e);
        });
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
        return trialCollection.whereNotIn("experimenter", excludedUsers).get().continueWith(task -> {
            List<Trial> trialList = new LinkedList<>();
            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                Trial trial = getProperTrial(doc);
                trialList.add(trial);
            }
            return trialList;
        }).addOnFailureListener(e -> {
            Log.e(TAG, "getExperimentTrials: Failed to get all user filtered trials for experiment " + experimentID, e);
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
        }).addOnFailureListener(e -> {
            Log.e(TAG, "addExperimentTrial: Failed to add trial to experiment " + experimentID, e);
        });
    }

}
