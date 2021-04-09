package com.example.crowderapp;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.*;


public class LocationMapTest {
    private Solo solo;
    String expName = "MapTestLOC";


    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class,
            true, true);


    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }


    @Test
    public void testMap() {
        solo.sleep(1000);
        UiTestHelperFunctions.createExperiment(solo, expName, 1, true, UiTestHelperFunctions.expTypes.COUNT);

        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);

        solo.sleep(1000);
        solo.clickOnText(expName);

        solo.sleep(1000);
        solo.clickOnView(solo.getView(android.R.id.button1));

        solo.sleep(1000);
        UiTestHelperFunctions.openHeatMap(solo);

        solo.sleep(2000);
        solo.assertCurrentActivity("Not in Heatmap Activity", HeatmapActivity.class);
    }


    // Heatmap Activity has no back button. Need to clean up experiments separately
    @Test
    public void locationManualCleanUp() {
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);

        while (solo.searchText("LOC__UI.TEST__")) {
            solo.clickOnText("LOC__UI.TEST__");
            solo.sleep(1000);
            solo.clickOnView(solo.getView(android.R.id.button1));
            UiTestHelperFunctions.unpublishExp(solo);
            solo.sleep(1000);
            UiTestHelperFunctions.goToMyExperiments(solo);
            solo.sleep(1000);
        }
    }
}
