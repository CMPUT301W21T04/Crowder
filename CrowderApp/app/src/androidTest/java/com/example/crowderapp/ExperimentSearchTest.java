package com.example.crowderapp;

import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.*;

import java.util.Arrays;
import java.util.List;


public class ExperimentSearchTest {
    private Solo solo;
    List<String> expNames = Arrays.asList("SearchExp Test", "Pikachu Test", "Charmander Test", "Agumon Test");


    @Rule
    public ActivityTestRule<MainActivity> testRule = new ActivityTestRule<>(MainActivity.class,
            true, true);


    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), testRule.getActivity());
    }


    @Test
    public void testAllExperiments() {
        solo.assertCurrentActivity("Not in Main Activity.", MainActivity.class);

        for (String name : expNames) {
            UiTestHelperFunctions.createExperiment(solo, name, 1);
            solo.sleep(1000);
        }

        EditText searchText = (EditText) solo.getView(R.id.search_EditText);
        for (String name : expNames) {
            solo.enterText(searchText, name);
            solo.clickOnView(solo.getView(R.id.search_btn));
            Assert.assertTrue(solo.searchText(name));
            solo.clearEditText(searchText);
        }

        // Clean up
        UiTestHelperFunctions.goToAllExperiments(solo);
        for (String name : expNames) {
            UiTestHelperFunctions.toggleSubExperiment(solo, name);
        }

        UiTestHelperFunctions.goToMyExperiments(solo);

        for (String name : expNames) {
            solo.clickOnText(name);
            UiTestHelperFunctions.unpublishExp(solo);
        }
    }
}
