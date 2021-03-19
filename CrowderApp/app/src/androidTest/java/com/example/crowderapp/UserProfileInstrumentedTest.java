package com.example.crowderapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
public class UserProfileInstrumentedTest {

    final String TAG = "UserProfileInstrumentedTest";

    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class, true, true);

    private Solo solo;

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }

    @Test
    public void testChangeInfo() {
        UiTestHelperFunctions.goToProfile(solo);

        View editButton = solo.getView(R.id.profile_edit);
        EditText userNameField = (EditText) solo.getView(R.id.profile_username);
        EditText phoneField = (EditText) solo.getView(R.id.profile_phone);
        EditText emailField = (EditText) solo.getView(R.id.profile_email);

        solo.clickOnView(editButton);
        solo.clearEditText(userNameField);
        solo.enterText(userNameField, "Test User 123");
        solo.clearEditText(phoneField);
        solo.enterText(phoneField, "9991112222");
        solo.clearEditText(emailField);
        solo.enterText(emailField, "test@email.com");
        solo.clickOnView(editButton);

        UiTestHelperFunctions.goToAllExperiments(solo);
        UiTestHelperFunctions.goToProfile(solo);

        Assert.assertTrue(solo.searchText("Test User 123"));
        Assert.assertTrue(solo.searchText("9991112222"));
        Assert.assertTrue(solo.searchText("test@email.com"));
    }
}
