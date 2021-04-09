package com.example.crowderapp;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.*;


public class StatisticsTest {
    private Solo solo;
    String expName = "Stats Test";


    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class,
            true, true);


    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }


    public void checkStatsText() {
        Assert.assertTrue(solo.searchText("Quartile 1"));
        Assert.assertTrue(solo.searchText("Quartile 2"));
        Assert.assertTrue(solo.searchText("Quartile 3"));
        Assert.assertTrue(solo.searchText("Median"));
        Assert.assertTrue(solo.searchText("Mean"));
        Assert.assertTrue(solo.searchText("Std. Dev."));
    }


    @Test
    public void testStats() {
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

            solo.sleep(1000);
            UiTestHelperFunctions.openStatistics(solo);

            solo.sleep(1000);
            solo.assertCurrentActivity("Not in Stats Activity", StatsActivity.class);

            checkStatsText();

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
