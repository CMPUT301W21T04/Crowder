package com.example.crowderapp;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.*;


public class ExperimentNameTest {
    private Solo solo;
    String expName = "MyTestExp";


    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class,
            true, true);


    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }


    @Test
    public void testExpName() {
        solo.assertCurrentActivity("Not in Main Activity.", MainActivity.class);

        UiTestHelperFunctions.createExperiment(solo, expName, 1, UiTestHelperFunctions.expTypes.COUNT);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expName);
        Assert.assertTrue(solo.searchText(expName));


        // Clean up
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expName);
        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);

        UiTestHelperFunctions.goToAllExperiments(solo);
        UiTestHelperFunctions.createExperiment(solo, expName, 1, UiTestHelperFunctions.expTypes.BINOMIAL);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expName);
        Assert.assertTrue(solo.searchText(expName));


        // Clean up
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expName);
        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);

        UiTestHelperFunctions.goToAllExperiments(solo);
        UiTestHelperFunctions.createExperiment(solo, expName, 1, UiTestHelperFunctions.expTypes.MEASUREMENT);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expName);
        Assert.assertTrue(solo.searchText(expName));


        // Clean up
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expName);
        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);

        UiTestHelperFunctions.goToAllExperiments(solo);
        UiTestHelperFunctions.createExperiment(solo, expName, 1, UiTestHelperFunctions.expTypes.TALLY);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expName);
        Assert.assertTrue(solo.searchText(expName));

        // Clean up
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expName);
        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);
    }
}
