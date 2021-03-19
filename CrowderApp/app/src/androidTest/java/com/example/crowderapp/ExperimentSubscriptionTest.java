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
    public void testSubscriptionAfterCreation() {
        solo.assertCurrentActivity("Not in Main Activity.", MainActivity.class);

        UiTestHelperFunctions.createExperiment(solo, expname, 1);

        solo.sleep(2000);

        ListView experimentsList = (ListView) solo.getView(R.id.all_experiment_list);
        ArrayAdapter adapter = (ArrayAdapter) experimentsList.getAdapter();

        int count = adapter.getCount();

        solo.sleep(2000);

        UiTestHelperFunctions.toggleSubExperiment(solo, count-1);

        UiTestHelperFunctions.goToMyExperiments(solo);
        Assert.assertTrue(solo.searchText(expname));

        solo.clickOnText(expname);
        View dropdown = solo.getView(R.id.more_item);
        solo.clickOnView(dropdown);
        solo.sleep(500);
        solo.clickOnText("Unpublish Experiment");
        UiTestHelperFunctions.goToAllExperiments(solo);
    }

    @Test
    public void testSubscriptionOnExisting() {
        solo.assertCurrentActivity("Not in Main Activity.", MainActivity.class);

        solo.sleep(3000);

        ListView experimentsList = (ListView) solo.getView(R.id.all_experiment_list);
        ArrayAdapter adapter = (ArrayAdapter) experimentsList.getAdapter();

        int count = adapter.getCount();

        if (count == 0) {
            UiTestHelperFunctions.createExperiment(solo, expname, 1);
            UiTestHelperFunctions.goToMyExperiments(solo);
            UiTestHelperFunctions.goToAllExperiments(solo);
            solo.sleep(2000);
        }

        AllExperimentListItem expItem = (AllExperimentListItem) adapter.getItem(0);
        Experiment exp = expItem.getExperiment();
        String currentName = exp.getName();

        UiTestHelperFunctions.toggleSubExperiment(solo, 0);

        UiTestHelperFunctions.goToMyExperiments(solo);

        Assert.assertTrue(solo.searchText(currentName));

        UiTestHelperFunctions.goToAllExperiments(solo);
        UiTestHelperFunctions.toggleSubExperiment(solo, 0);

        UiTestHelperFunctions.goToMyExperiments(solo);
        Assert.assertFalse(solo.searchText(currentName));
        UiTestHelperFunctions.goToAllExperiments(solo);
    }
}
