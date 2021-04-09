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
    public void testStatsCount() {
        solo.sleep(1000);
        UiTestHelperFunctions.createExperiment(solo, expName, 1, UiTestHelperFunctions.expTypes.COUNT);

        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);

        solo.sleep(1000);
        solo.clickOnText(expName);

        solo.sleep(1000);
        UiTestHelperFunctions.openStatistics(solo);

        solo.sleep(1000);
        solo.assertCurrentActivity("Not in Stats Activity", StatsActivity.class);

        checkStatsText();
    }


    @Test
    public void testStatsBinomial() {
        solo.sleep(1000);
        UiTestHelperFunctions.createExperiment(solo, expName, 1, UiTestHelperFunctions.expTypes.BINOMIAL);

        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);

        solo.sleep(1000);
        solo.clickOnText(expName);

        solo.sleep(1000);
        UiTestHelperFunctions.openStatistics(solo);

        solo.sleep(1000);
        solo.assertCurrentActivity("Not in Stats Activity", StatsActivity.class);

        checkStatsText();

    }


    @Test
    public void testStatsMeasurement() {
        solo.sleep(1000);
        UiTestHelperFunctions.createExperiment(solo, expName, 1, UiTestHelperFunctions.expTypes.MEASUREMENT);

        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);

        solo.sleep(1000);
        solo.clickOnText(expName);

        solo.sleep(1000);
        UiTestHelperFunctions.openStatistics(solo);

        solo.sleep(1000);
        solo.assertCurrentActivity("Not in Stats Activity", StatsActivity.class);

        checkStatsText();

    }


    @Test
    public void testStatsTally() {
        solo.sleep(1000);
        UiTestHelperFunctions.createExperiment(solo, expName, 1, UiTestHelperFunctions.expTypes.TALLY);

        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);

        solo.sleep(1000);
        solo.clickOnText(expName);

        solo.sleep(1000);
        UiTestHelperFunctions.openStatistics(solo);

        solo.sleep(1000);
        solo.assertCurrentActivity("Not in Stats Activity", StatsActivity.class);

        checkStatsText();

    }


    // Stats Activity has no back button. Need to clean up experiments separately
    @Test
    public void manualCleanUp() {
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);

        while (solo.searchText("__UI.TEST__")) {
            solo.clickOnText("__UI.TEST__");
            solo.sleep(1000);
            UiTestHelperFunctions.unpublishExp(solo);
            solo.sleep(1000);
            UiTestHelperFunctions.goToMyExperiments(solo);
            solo.sleep(1000);
        }

    }
}
