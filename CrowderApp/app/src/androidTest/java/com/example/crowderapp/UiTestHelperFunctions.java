package com.example.crowderapp;

import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;

public class UiTestHelperFunctions {

    public enum expTypes {
        COUNT,
        BINOMIAL,
        TALLY,
        MEASUREMENT
    }

    public static void createExperiment(Solo solo, String expName, expTypes type) {
        FloatingActionButton addExpButton = (FloatingActionButton) solo.getView(R.id.add_experiment_button);
        EditText expNameText = (EditText) solo.getView(R.id.experiment_name_EditText);
        // do we need to test for different cases of this?
        Spinner dropDown = (Spinner) solo.getView(R.id.dropdown);
        EditText minTrials = (EditText) solo.getView(R.id.min_trials_EditText);

        solo.clickOnView(addExpButton);
        solo.enterText(expNameText, expName);
        solo.clickOnView(dropDown);
    }

    public static void createExperiment(Solo solo, String expName) {
        createExperiment(solo, expName, expTypes.COUNT);
    }
}
