package com.example.crowderapp;

import android.widget.EditText;

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
public class CommentTest {
    private Solo solo;
    String expname = "Comment Test";
    String question = "test question";

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }

    @Test
    public void testPostQuestion() {
        UiTestHelperFunctions.createExperiment(solo, expname, 1);

        solo.clickOnText(expname);

        FloatingActionButton addQuestButton = (FloatingActionButton) solo.getView(R.id.add_question_button);
        solo.clickOnView(addQuestButton);

        solo.sleep(1000);

        EditText questText = (EditText) solo.getView(R.id.question_EditText);

        solo.enterText(questText, "");
        solo.clickOnView(solo.getView(android.R.id.button1));

        Assert.assertTrue(solo.searchText("Add Question"));

        solo.enterText(questText, question);
        solo.clickOnView(solo.getView(android.R.id.button1));

        Assert.assertTrue(solo.searchText(question));

        solo.sleep(500);

        UiTestHelperFunctions.goToAllExperiments(solo);

        solo.clickOnText(expname);

        Assert.assertTrue(solo.searchText(question));

        solo.sleep(500);

        //cleanup
        UiTestHelperFunctions.goToAllExperiments(solo);
        UiTestHelperFunctions.toggleSubExperiment(solo, expname);
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expname);
        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
    }
}
