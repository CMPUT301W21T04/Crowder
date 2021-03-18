package com.example.crowderapp;

import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.allExperimentsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.createExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.dao.ExperimentFSDAO;
import com.example.crowderapp.models.dao.ExperimentMockDAO;
import com.example.crowderapp.models.dao.UserFSDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Config(sdk = 27)
@RunWith(RobolectricTestRunner.class)
public class ExperimentHandlerUnitTest {
    String expID = "";
    ArrayList<Experiment> experiments = new ArrayList<Experiment>();

    // Enables callbacks to complete before ending test
    public void finishAllTasks() {
        ShadowLooper sl = ShadowLooper.shadowMainLooper();
        sl.runToEndOfTasks();
    }


    @Test
    public void testCreateExperiment() {
        ExperimentMockDAO dao = new ExperimentMockDAO();
        ExperimentHandler handler = new ExperimentHandler(dao);

        handler.getAllExperiments(new allExperimentsCallBack() {
            @Override
            public void callBackResult(List<Experiment> experimentList) {
                experiments = (ArrayList<Experiment>) experimentList;
            }
        });
        finishAllTasks();

        Assert.assertTrue(experiments.isEmpty());

        // Creating first experiment
        String expName1 = "Experiment1";
        boolean loc1 = false;
        int minT1 = 0;
        String eType1 = "BinomialTrial";

        handler.createExperiment(expName1, loc1, minT1, eType1, new createExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {
                expID = experiment.getExperimentID();
                Assert.assertEquals(experiment.getName(), expName1);
                Assert.assertFalse(experiment.isLocationRequired());
                Assert.assertEquals(experiment.getMinTrials(), minT1);
                Assert.assertEquals(experiment.getExperimentType(), eType1);
            }
        });
        finishAllTasks();

        handler.getAllExperiments(new allExperimentsCallBack() {
            @Override
            public void callBackResult(List<Experiment> experimentList) {
                experiments = (ArrayList<Experiment>) experimentList;
            }
        });
        finishAllTasks();

        Assert.assertEquals(1, experiments.size());
        Assert.assertEquals(expID, experiments.get(0).getExperimentID());

        // Create second experiment
        String expName2 = "Experiment2";
        boolean loc2 = true;
        int minT2 = 0;
        String eType2 = "BinomialTrial";
        handler.createExperiment(expName2, loc2, minT2, eType2, new createExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {
                expID = experiment.getExperimentID();
                Assert.assertEquals(experiment.getName(), expName2);
                Assert.assertTrue(experiment.isLocationRequired());
                Assert.assertEquals(experiment.getMinTrials(), minT2);
                Assert.assertEquals(experiment.getExperimentType(), eType2);
            }
        });
        finishAllTasks();

        handler.getAllExperiments(new allExperimentsCallBack() {
            @Override
            public void callBackResult(List<Experiment> experimentList) {
                experiments = (ArrayList<Experiment>) experimentList;
            }
        });
        finishAllTasks();

        Assert.assertEquals(2, experiments.size());
        Assert.assertEquals(experiments.get(1).getExperimentID(), expID);
    }
}
