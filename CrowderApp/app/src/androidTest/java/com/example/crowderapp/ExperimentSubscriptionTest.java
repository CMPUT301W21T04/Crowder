package com.example.crowderapp;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.crowderapp.models.AllExperimentListItem;
import com.example.crowderapp.models.Experiment;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ExperimentSubscriptionTest {
    private Solo solo;
    String expname = "Sub Test";

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }

    @Test
    public void testSubscription() {
        solo.assertCurrentActivity("Not in Main Activity.", MainActivity.class);

        UiTestHelperFunctions.createExperiment(solo, expname, 1);

        solo.sleep(2000);

        UiTestHelperFunctions.toggleSubExperiment(solo, expname);

        UiTestHelperFunctions.goToMyExperiments(solo);
        Assert.assertTrue(solo.searchText(expname));

        UiTestHelperFunctions.goToAllExperiments(solo);
        UiTestHelperFunctions.toggleSubExperiment(solo, expname);
        UiTestHelperFunctions.goToMyExperiments(solo);

        Assert.assertFalse(solo.searchText(expname));

        //cleanup
        UiTestHelperFunctions.goToAllExperiments(solo);
        UiTestHelperFunctions.toggleSubExperiment(solo, expname);
        UiTestHelperFunctions.goToMyExperiments(solo);

        solo.clickOnText(expname);

        UiTestHelperFunctions.unpublishExp(solo);

        UiTestHelperFunctions.goToAllExperiments(solo);
    }
}
