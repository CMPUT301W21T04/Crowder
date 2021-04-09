package com.example.crowderapp;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class QRCodeTest {

    static List<String> expNames = Arrays.asList("CountQRTest", "BinQRTest", "MeasureQRTest", "TallyQRTest");

    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }


    @Test
    public void testQRGenerateCount() {
        String expname = expNames.get(0);

        UiTestHelperFunctions.createExperiment(solo, expname, 1, UiTestHelperFunctions.expTypes.COUNT);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);
        solo.clickOnText(expname);

        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(1500);
        solo.clickOnText("Generate QR Codes");

        solo.assertCurrentActivity("Not in QRCodeActivity.", QRCodeActivity.class);
        
    }


    @Test
    public void testQRGenerateBinomial() {
        String expname = expNames.get(1);

        UiTestHelperFunctions.createExperiment(solo, expname, 1, UiTestHelperFunctions.expTypes.BINOMIAL);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);
        solo.clickOnText(expname);

        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(1500);
        solo.clickOnText("Generate QR Codes");

        Spinner dropDown = (Spinner) solo.getView(R.id.dropdown_binomial);
        solo.sleep(1000);

        solo.pressSpinnerItem(0, 1);
        solo.clickOnView(solo.getView(android.R.id.button1));

        solo.assertCurrentActivity("Not in QRCodeActivity.", QRCodeActivity.class);
    }


    @Test
    public void testQRGenerateMeasurement() {
        String expname = expNames.get(2);

        UiTestHelperFunctions.createExperiment(solo, expname, 1, UiTestHelperFunctions.expTypes.MEASUREMENT);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);
        solo.clickOnText(expname);

        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(1500);
        solo.clickOnText("Generate QR Codes");

        EditText measureText = (EditText) solo.getView(R.id.measure_EditText);
        solo.enterText(measureText, "100");
        solo.sleep(1000);

        solo.clickOnView(solo.getView(android.R.id.button1));

        solo.assertCurrentActivity("Not in QRCodeActivity.", QRCodeActivity.class);
    }


    @Test
    public void testQRGenerateTally() {
        String expname = expNames.get(3);

        UiTestHelperFunctions.createExperiment(solo, expname, 1, UiTestHelperFunctions.expTypes.TALLY);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);
        solo.clickOnText(expname);

        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(1500);
        solo.clickOnText("Generate QR Codes");

        EditText TallyText = (EditText) solo.getView(R.id.non_neg_EditText);
        solo.enterText(TallyText, "100");
        solo.sleep(1000);

        solo.clickOnView(solo.getView(android.R.id.button1));

        solo.assertCurrentActivity("Not in QRCodeActivity.", QRCodeActivity.class);
    }

    // QR activity has no back button so  we delete in a separate test
    // Must be the last test to run, unfortunately JUnit4 runs tests in a deterministic but
    // unpredictable ordering way
    @Test
    public void manualCleanUp() {
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);

        while (solo.searchText("__UI.TEST__")) {
            solo.clickOnText("__UI.TEST__");
            UiTestHelperFunctions.unpublishExp(solo);
            solo.sleep(1000);
            UiTestHelperFunctions.goToMyExperiments(solo);
            solo.sleep(1000);
        }
    }
}
