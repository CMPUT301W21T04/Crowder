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
    String reply = "reply";

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }

    @Test
    public void testTitleBar() {
        UiTestHelperFunctions.createExperiment(solo, expname, 1);

        solo.clickOnText(expname);

        Assert.assertTrue(solo.searchText("Questions"));

        FloatingActionButton addQuestButton = (FloatingActionButton) solo.getView(R.id.add_question_button);
        solo.clickOnView(addQuestButton);

        solo.sleep(1000);

        EditText questText = (EditText) solo.getView(R.id.question_EditText);

        solo.enterText(questText, question);
        solo.clickOnView(solo.getView(android.R.id.button1));
        solo.clickOnText(question);

        Assert.assertTrue(solo.searchText("Replies"));

        // Clean up
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expname);
        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
    }

    @Test
    public void testPostQuestion() {
        UiTestHelperFunctions.createExperiment(solo, expname, 1);

        solo.clickOnText(expname);

        FloatingActionButton addQuestButton = (FloatingActionButton) solo.getView(R.id.add_question_button);
        solo.clickOnView(addQuestButton);

        solo.sleep(1000);

        EditText questText = (EditText) solo.getView(R.id.question_EditText);

        // test empty question
        solo.enterText(questText, "");
        solo.clickOnView(solo.getView(android.R.id.button1));

        Assert.assertTrue(solo.searchText("Add Question"));

        // test creating question
        solo.enterText(questText, question);
        solo.clickOnView(solo.getView(android.R.id.button1));

        Assert.assertTrue(solo.searchText(question));

        // test question persists
        solo.sleep(500);

        UiTestHelperFunctions.goToAllExperiments(solo);

        solo.clickOnText(expname);

        Assert.assertTrue(solo.searchText(question));

        // test reply
        solo.clickOnText(question);

        FloatingActionButton addAnsButton = (FloatingActionButton) solo.getView(R.id.add_reply_button);
        solo.clickOnView(addAnsButton);

        solo.sleep(1000);

        EditText ansText = (EditText) solo.getView(R.id.reply_EditText);

        // test empty reply
        solo.enterText(ansText, "");
        solo.clickOnView(solo.getView(android.R.id.button1));

        Assert.assertTrue(solo.searchText("Add Reply"));

        // Test reply
        solo.enterText(ansText, reply);
        solo.clickOnView(solo.getView(android.R.id.button1));

        Assert.assertTrue(solo.searchText(reply));

        // test reply persistence
        UiTestHelperFunctions.goToAllExperiments(solo);
        solo.clickOnText(expname);
        solo.clickOnText(question);

        Assert.assertTrue(solo.searchText(reply));

        solo.sleep(500);

        //cleanup
        UiTestHelperFunctions.goToMyExperiments(solo);
        solo.clickOnText(expname);
        UiTestHelperFunctions.unpublishExp(solo);
        solo.sleep(1000);
    }
}
