package com.example.crowderapp;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExperimentEndTest {
    private Solo solo;
    String expname = "End Test";

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }

    @Test
    public void testExpEndCount() {
        UiTestHelperFunctions.initExp(solo, expname, UiTestHelperFunctions.expTypes.COUNT);

        Button incrementButton = (Button) solo.getView(R.id.count_button);
        Button saveButton = (Button) solo.getView(R.id.count_save_Button);
        solo.clickOnView(incrementButton);
        UiTestHelperFunctions.endExp(solo);

        solo.clickOnView(incrementButton);
        Assert.assertTrue(solo.searchText("1"));
        solo.clickOnView(saveButton);
        Assert.assertTrue(solo.searchText("1"));

        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expname);
        solo.clickOnView(incrementButton);
        Assert.assertTrue(solo.searchText("0"));

        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
    }

    @Test
    public void testExpEndBinomial() {
        UiTestHelperFunctions.initExp(solo, expname, UiTestHelperFunctions.expTypes.BINOMIAL);

        Button incrementButton = (Button) solo.getView(R.id.binomialPassButton);
        Button saveButton = (Button) solo.getView(R.id.binomialSave);
        solo.clickOnView(incrementButton);
        UiTestHelperFunctions.endExp(solo);

        solo.clickOnView(incrementButton);
        Assert.assertTrue(solo.searchText("Successes: 1"));
        solo.clickOnView(saveButton);
        Assert.assertTrue(solo.searchText("Successes: 1"));

        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expname);
        solo.clickOnView(incrementButton);
        Assert.assertTrue(solo.searchText("Successes: 0"));

        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
    }

    @Test
    public void testExpEndTally() {
        UiTestHelperFunctions.initExp(solo, expname, UiTestHelperFunctions.expTypes.TALLY);

        Button incrementButton = (Button) solo.getView(R.id.non_neg_button_enter);
        EditText measurement = (EditText) solo.getView(R.id.non_neg_value_editText);
        Button saveButton = (Button) solo.getView(R.id.non_neg_button_save);
        solo.enterText(measurement, "1");
        solo.clickOnView(incrementButton);
        UiTestHelperFunctions.endExp(solo);

        solo.enterText(measurement, "1");
        solo.clickOnView(incrementButton);
        Assert.assertTrue(solo.searchText("1.00"));
        solo.clickOnView(saveButton);
        Assert.assertTrue(solo.searchText("1.00"));

        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expname);
        Assert.assertFalse(solo.searchText("1.00"));

        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
    }

    @Test
    public void testExpEndMeasurement() {
        UiTestHelperFunctions.initExp(solo, expname, UiTestHelperFunctions.expTypes.MEASUREMENT);

        Button incrementButton = (Button) solo.getView(R.id.measurement_button_enter);
        EditText measurement = (EditText) solo.getView(R.id.measurement_value_editText);
        Button saveButton = (Button) solo.getView(R.id.measurement_button_save);
        solo.enterText(measurement, "1");
        solo.clickOnView(incrementButton);
        UiTestHelperFunctions.endExp(solo);

        solo.enterText(measurement, "1");
        solo.clickOnView(incrementButton);
        Assert.assertTrue(solo.searchText("1.00"));
        solo.clickOnView(saveButton);
        Assert.assertTrue(solo.searchText("1.00"));

        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expname);
        Assert.assertFalse(solo.searchText("1.00"));

        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
    }
}
