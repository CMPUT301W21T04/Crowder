package com.example.crowderapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;

public class UiTestHelperFunctions {

    public enum expTypes {
        COUNT,
        BINOMIAL,
        TALLY,
        MEASUREMENT
    }

    public static void createExperiment(Solo solo, String expName, int minTrialAmt, expTypes type) {
        FloatingActionButton addExpButton = (FloatingActionButton) solo.getView(R.id.add_experiment_button);

        solo.clickOnView(addExpButton);

        EditText expNameText = (EditText) solo.getView(R.id.experiment_name_EditText);
        Spinner dropDown = (Spinner) solo.getView(R.id.dropdown);
        EditText minTrials = (EditText) solo.getView(R.id.min_trials_EditText);

        solo.enterText(expNameText, expName);
        solo.clickOnView(dropDown);

        solo.sleep(2000);

        switch (type) {
            case COUNT:
                solo.clickOnText("Count");
                break;
            case BINOMIAL:
                solo.clickOnText("Binomial");
                break;
            case TALLY:
                solo.clickOnText("Non-Negative Integer");
                break;
            case MEASUREMENT:
                solo.clickOnText("Measurement");
                break;
            default:
                solo.clickOnText("Count");
        }

        solo.enterText(minTrials, String.valueOf(minTrialAmt));
        // https://stackoverflow.com/questions/10359192/how-to-select-which-button-to-click-on-robotium-for-an-alert-dialog/10858118
        solo.clickOnView(solo.getView(android.R.id.button1));
    }

    public static void createExperiment(Solo solo, String expName, int minTrialAmt) {
        createExperiment(solo, expName, minTrialAmt, expTypes.COUNT);
    }

    public static void goToProfile(Solo solo) {
        solo.clickOnText("Profile");
    }

    public static void goToMyExperiments(Solo solo) {
        solo.clickOnText("My Experiments");
    }

    public static void goToAllExperiments(Solo solo) {
        solo.clickOnText("All Experiments");
    }

    public static void toggleSubExperiment(Solo solo, String expname) {
        //https://stackoverflow.com/questions/22299328/how-to-click-button-adjacent-to-a-specific-text-in-robotium
        TextView expText = solo.getText(expname);
        ViewGroup expPair = (ViewGroup) expText.getParent();
        Button subButton = (Button) expPair.getChildAt(1);
        solo.clickOnView(subButton);
    }

    public static void initExp(Solo solo, String expname, UiTestHelperFunctions.expTypes type) {
        solo.assertCurrentActivity("Not in Main Activity.", MainActivity.class);

        createExperiment(solo, expname, 1, type);

        solo.sleep(2000);

        toggleSubExperiment(solo, expname);

        goToMyExperiments(solo);
        solo.clickOnText(expname);
    }

    public static void endExp(Solo solo) {
        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(1500);
        solo.clickOnText("End Experiment");
    }

    public static void unpublishExp(Solo solo) {
        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(1500);
        solo.clickOnText("Unpublish Experiment");
    }
}
