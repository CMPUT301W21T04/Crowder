package com.example.crowderapp;

import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.createExperimentCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.dao.ExperimentMockDAO;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import java.util.concurrent.TimeUnit;

@Config(sdk = 27)
@RunWith(RobolectricTestRunner.class)
public class ExperimentHandlerUnitTest {

    public void finishAllTasks() {
        ShadowLooper sl = ShadowLooper.shadowMainLooper();
        sl.runToEndOfTasks();
    }

    @Test
    public void testCreateExperiment() throws InterruptedException {
        ExperimentMockDAO dao = new ExperimentMockDAO();
        ExperimentHandler handler = new ExperimentHandler(dao);

        String expName = "Dan";
        boolean loc = false;
        int minT = 0;
        String eType = "BinomialTrial";


        handler.createExperiment(expName, loc, minT, eType, new createExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {
                System.out.println("Name: " + experiment.getName());
            }
        });


        finishAllTasks();

    }
}
