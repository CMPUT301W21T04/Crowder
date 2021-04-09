package com.example.crowderapp;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.*;

import static com.example.crowderapp.UiTestHelperFunctions.deleteSuffix;

public class UnpublishExperimentTest {
    private Solo solo;
    String expName = "Unpublish Test";


    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class,
            true, true);


    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }


    @Test
    public void testUnpublish() {
        solo.sleep(1000);
        UiTestHelperFunctions.createExperiment(solo, expName, 1, false, false, UiTestHelperFunctions.expTypes.COUNT);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.sleep(1000);
        solo.clickOnText(expName);
        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
        UiTestHelperFunctions.goToMyExperiments(solo);
        Assert.assertTrue(solo.searchText(expName));
    }

}
