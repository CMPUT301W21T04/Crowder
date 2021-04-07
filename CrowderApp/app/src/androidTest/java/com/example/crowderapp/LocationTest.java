package com.example.crowderapp;

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
public class LocationTest {
    private Solo solo;
    String expname = "Location Test";

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }

    @Test
    public void testLocationPrompt() {
        solo.assertCurrentActivity("Not in Main Activity.", MainActivity.class);

        UiTestHelperFunctions.createExperiment(solo, expname, 1);

        solo.sleep(2000);

        UiTestHelperFunctions.toggleSubExperiment(solo, expname);

        UiTestHelperFunctions.goToMyExperiments(solo);

        //finish this later
    }
}
