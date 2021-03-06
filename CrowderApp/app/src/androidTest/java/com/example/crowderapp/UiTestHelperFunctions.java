package com.example.crowderapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;

import org.junit.Assert;

public class UiTestHelperFunctions {

    // Experiment deletes on unpublish
    // Must match R.string.deletableExpSuffix in string.xml
    protected static String deleteSuffix = "__UI.TEST__";

    public enum expTypes {
        COUNT,
        BINOMIAL,
        TALLY,
        MEASUREMENT
    }


    public static void createExperiment(Solo solo, String expName, int minTrialAmt, boolean enableLocation, boolean isDeletable, expTypes type) {
        FloatingActionButton addExpButton = (FloatingActionButton) solo.getView(R.id.add_experiment_button);

        solo.clickOnView(addExpButton);

        EditText expNameText = (EditText) solo.getView(R.id.experiment_name_EditText);
        EditText minTrials = (EditText) solo.getView(R.id.min_trials_EditText);
        if (isDeletable)
            solo.enterText(expNameText, expName + deleteSuffix);
        else
            solo.enterText(expNameText, expName);

        solo.enterText(minTrials, String.valueOf(minTrialAmt));

        // Add region to constructor of createExperiment later on
        EditText regionText = (EditText) solo.getView(R.id.region_EditText);
        solo.enterText(regionText, "North America");

        Spinner dropDown = (Spinner) solo.getView(R.id.dropdown);
        solo.sleep(1000);

        switch (type) {
            case COUNT:
                solo.pressSpinnerItem(0, 1);
                break;
            case BINOMIAL:
                solo.pressSpinnerItem(0, 2);
                break;
            case TALLY:
                solo.pressSpinnerItem(0, 3);
                break;
            case MEASUREMENT:
                solo.pressSpinnerItem(0, 4);
                break;
            default:
                solo.pressSpinnerItem(0, 1);
        }

        if (enableLocation) {
            solo.clickOnCheckBox(0);
        }

        // https://stackoverflow.com/questions/10359192/how-to-select-which-button-to-click-on-robotium-for-an-alert-dialog/10858118
        // by Avi Cherry
        // licence (2nd answer) CC BY-SA 3.0
        solo.clickOnView(solo.getView(android.R.id.button1));
    }


    // Location disabled experiment
//    public static void createExperiment(Solo solo, String expName, int minTrialAmt, expTypes type) {
//        FloatingActionButton addExpButton = (FloatingActionButton) solo.getView(R.id.add_experiment_button);
//
//        solo.clickOnView(addExpButton);
//
//        EditText expNameText = (EditText) solo.getView(R.id.experiment_name_EditText);
//        EditText minTrials = (EditText) solo.getView(R.id.min_trials_EditText);
//        solo.enterText(expNameText, expName + deleteSuffix);
//        solo.enterText(minTrials, String.valueOf(minTrialAmt));
//
//        // TODO: Add region to constructor of createExperiment
//        EditText regionText = (EditText) solo.getView(R.id.region_EditText);
//        solo.enterText(regionText, "North America");
//
//        Spinner dropDown = (Spinner) solo.getView(R.id.dropdown);
//        solo.sleep(1000);
//
//        switch (type) {
//            case COUNT:
//                solo.pressSpinnerItem(0, 1);
//                break;
//            case BINOMIAL:
//                solo.pressSpinnerItem(0, 2);
//                break;
//            case TALLY:
//                solo.pressSpinnerItem(0, 3);
//                break;
//            case MEASUREMENT:
//                solo.pressSpinnerItem(0, 4);
//                break;
//            default:
//                solo.pressSpinnerItem(0, 1);
//        }
//
//        // https://stackoverflow.com/questions/10359192/how-to-select-which-button-to-click-on-robotium-for-an-alert-dialog/10858118
//        solo.clickOnView(solo.getView(android.R.id.button1));
//    }


    // Default experiment type
    public static void createExperiment(Solo solo, String expName, int minTrialAmt) {
        createExperiment(solo, expName, minTrialAmt, false, true, expTypes.COUNT);
    }

    public static void goToMyExperiments(Solo solo) {
        View myButton = solo.getView(R.id.navigation_my_experiments);
        solo.clickOnView(myButton);
    }

    public static void goToAllExperiments(Solo solo) {
        View allButton = solo.getView(R.id.navigation_all_experiments);
        solo.clickOnView(allButton);
    }

    public static void goToProfile(Solo solo) {
        View profButton = solo.getView(R.id.navigation_profile);
        solo.sleep(1000);
        solo.clickOnView(profButton);
    }

    public static void toggleSubExperiment(Solo solo, String expname) {
        //https://stackoverflow.com/questions/22299328/how-to-click-button-adjacent-to-a-specific-text-in-robotium
        // by Paul Harris
        // licence: CC BY-SA 3.0
        Assert.assertTrue(solo.waitForText(expname,1, 50000,  true));
        TextView expText = solo.getText(expname);
        ViewGroup expPair = (ViewGroup) expText.getParent();
        Button subButton = (Button) expPair.getChildAt(1);
        solo.clickOnView(subButton);
    }

    public static void initExp(Solo solo, String expname, UiTestHelperFunctions.expTypes type) {
        solo.assertCurrentActivity("Not in Main Activity.", MainActivity.class);

        createExperiment(solo, expname, 1, false, true, type);

        solo.sleep(2000);

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


    public static void openHeatMap(Solo solo) {
        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(1500);
        solo.clickOnText("Location Heatmap");
    }

    public static void ignoreSelfData(Solo solo) {
        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(1500);
        solo.clickOnText("User Filter");
        solo.clickOnCheckBox(0);
        solo.clickOnView(solo.getView(android.R.id.button1));
    }

    public static void openStatistics(Solo solo) {
        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(1500);
        solo.clickOnText("Statistics");
    }
}
