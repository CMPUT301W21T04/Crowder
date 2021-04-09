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


@RunWith(AndroidJUnit4.class)
public class QRCodeTest {


    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }

    @Test
    public void testQRGenerate() {
        String expName;

        for (UiTestHelperFunctions.expTypes type : UiTestHelperFunctions.expTypes.values()) {
            solo.sleep(1000);

            expName = type.toString();

            UiTestHelperFunctions.createExperiment(solo, expName, 1, false,
                    true, type);
            solo.sleep(1000);
            UiTestHelperFunctions.goToMyExperiments(solo);
            solo.sleep(1000);
            solo.clickOnText(expName);
            View dropdown = solo.getView(R.id.more_item);
            solo.clickOnView(dropdown);
            solo.sleep(1500);
            solo.clickOnText("Generate QR Codes");

            solo.sleep(1000);
            if (type.equals(UiTestHelperFunctions.expTypes.BINOMIAL)) {
                Spinner dropDown = (Spinner) solo.getView(R.id.dropdown_binomial);
                solo.sleep(1000);
                solo.pressSpinnerItem(0, 1);
                solo.clickOnView(solo.getView(android.R.id.button1));
            } else if (type.equals(UiTestHelperFunctions.expTypes.MEASUREMENT)) {
                EditText measureText = (EditText) solo.getView(R.id.measure_EditText);
                solo.enterText(measureText, "100");
                solo.sleep(1000);

                solo.clickOnView(solo.getView(android.R.id.button1));
            } else if (type.equals(UiTestHelperFunctions.expTypes.TALLY)) {
                EditText TallyText = (EditText) solo.getView(R.id.non_neg_EditText);
                solo.enterText(TallyText, "100");
                solo.clickOnView(solo.getView(android.R.id.button1));
                solo.sleep(1000);
            }

            solo.assertCurrentActivity("Not in QRCodeActivity.", QRCodeActivity.class);

            solo.goBack();
            solo.sleep(1000);
            UiTestHelperFunctions.goToAllExperiments(solo);
            solo.sleep(1000);
        }

        // Clean up
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);
        while (solo.searchText("__UI.TEST__")) {
            solo.sleep(1500);
            solo.clickOnText("__UI.TEST__");
            UiTestHelperFunctions.unpublishExp(solo);
            solo.sleep(1000);
            UiTestHelperFunctions.goToMyExperiments(solo);
        }
    }
}
