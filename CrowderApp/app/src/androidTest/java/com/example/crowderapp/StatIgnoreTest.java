package com.example.crowderapp;

import android.widget.Button;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class StatIgnoreTest {
    private Solo solo;
    String expname = "End Test.__UI.TEST__";

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }

    @Test
    public void testIgnoreData() {
        UiTestHelperFunctions.initExp(solo, expname, UiTestHelperFunctions.expTypes.COUNT);

        Button count = (Button) solo.getView(R.id.count_button);

        solo.clickOnView(count);

        Button save = (Button) solo.getView(R.id.count_save_Button);

        solo.clickOnView(count);

        solo.sleep(1000);

        UiTestHelperFunctions.ignoreSelfData(solo);

        UiTestHelperFunctions.openStatistics(solo);

        Assert.assertFalse(solo.searchText("1"));

        solo.goBack();

        UiTestHelperFunctions.unpublishExp(solo);
    }
}
