package com.example.crowderapp;

import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.addTrialCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.allExperimentsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.createExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getAllSubscribedExperimentsCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.unPublishExperimentCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.dao.ExperimentFSDAO;
import com.example.crowderapp.models.dao.ExperimentMockDAO;
import com.example.crowderapp.models.dao.TrialFSDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.junit.Ignore;
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
        String ownerID = "1";
        String region = "North America";

        handler.createExperiment(expName1, loc1, region, minT1, eType1, ownerID , new createExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {
                expID = experiment.getExperimentID();
                Assert.assertEquals(experiment.getName(), expName1);
                Assert.assertFalse(experiment.isLocationRequired());
                Assert.assertEquals(experiment.getMinTrials(), minT1);
                Assert.assertEquals(experiment.getExperimentType(), eType1);
                Assert.assertEquals(experiment.getOwnerID(), ownerID);
                Assert.assertEquals(experiment.getRegion(), region);

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
        String ownerID2 = "2";
        String region2 = "South America";

        handler.createExperiment(expName2, loc2, region2, minT2, eType2, ownerID2, new createExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {
                expID = experiment.getExperimentID();
                Assert.assertEquals(experiment.getName(), expName2);
                Assert.assertTrue(experiment.isLocationRequired());
                Assert.assertEquals(experiment.getMinTrials(), minT2);
                Assert.assertEquals(experiment.getExperimentType(), eType2);
                Assert.assertEquals(experiment.getOwnerID(), ownerID2);
                Assert.assertEquals(experiment.getRegion(), region2);
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

    @Test
    public void unPublishExperimentTest(){

        // setup
        ExperimentFSDAO dao = mock(ExperimentFSDAO.class, RETURNS_DEEP_STUBS);
        Task<Experiment> task = mock(Task.class, RETURNS_DEEP_STUBS);
        ExperimentHandler handler = new ExperimentHandler(dao);

        when(dao.getExperiment(any())).thenReturn(task);

        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);
        unPublishExperimentCallBack mockedExperimentCB = mock(unPublishExperimentCallBack.class);

        handler.unPublishExperiment(any(), mockedExperimentCB);

        finishAllTasks();

        //testing

        verify(dao, times(1)).getExperiment(any()); // checks if this is called once
        verify(task, times(1)).addOnCompleteListener(captor.capture());
        when(task.isSuccessful()).thenReturn(true);
        captor.getValue().onComplete(task);

        verify(mockedExperimentCB, times(1)).callBackResult(); //callback check

    }

    @Ignore // Need to fix since TrialDAO not injectable
    @Test
    public void getExperimentTest() {
        ExperimentFSDAO dao = mock(ExperimentFSDAO.class, RETURNS_DEEP_STUBS);
        Task<Experiment> task = mock(Task.class, RETURNS_DEEP_STUBS);
        ExperimentHandler handler = new ExperimentHandler(dao);

        when(dao.getExperiment(any())).thenReturn(task);

        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);
        getExperimentCallBack getExperimentCB = mock(getExperimentCallBack.class);

        handler.getExperiment(any(), getExperimentCB); // execute code

        finishAllTasks();

        // testing

        verify(dao, times(1)).getExperiment(any());
        verify(task, times(1)).addOnCompleteListener(captor.capture());

        when(task.isSuccessful()).thenReturn(true);

        captor.getValue().onComplete(task);

        verify(getExperimentCB, times(1)).callBackResult(any());

    }

    @Test
    public void getAllExperimentsTest() {
        ExperimentFSDAO dao = mock(ExperimentFSDAO.class, RETURNS_DEEP_STUBS);
        Task<List<Experiment>> task = mock(Task.class, RETURNS_DEEP_STUBS);
        ExperimentHandler handler = new ExperimentHandler(dao);

        when(dao.getAllExperiments()).thenReturn(task);

        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);
        allExperimentsCallBack getExperimentAllExperimentsCB = mock(allExperimentsCallBack.class);

        handler.getAllExperiments(getExperimentAllExperimentsCB);

        finishAllTasks();

        verify(dao, times(1)).getAllExperiments();
        verify(task, times(1)).addOnCompleteListener(captor.capture());

        when(task.isSuccessful()).thenReturn(true);

        captor.getValue().onComplete(task);

        verify(getExperimentAllExperimentsCB, times(1)).callBackResult(any());

    }


    @Test
    public void getAllSubscribedExperimentsTest() {
        ExperimentFSDAO dao = mock(ExperimentFSDAO.class, RETURNS_DEEP_STUBS);
        Task<List<Experiment>> task = mock(Task.class, RETURNS_DEEP_STUBS);
        ExperimentHandler handler = new ExperimentHandler(dao);

        when(dao.getUserExperiments(any())).thenReturn(task);
        when(task.isSuccessful()).thenReturn(true);

        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);
        getAllSubscribedExperimentsCallBack cb = mock(getAllSubscribedExperimentsCallBack.class);

        handler.getAllSubscribedExperiments(any(), cb);

        finishAllTasks();

        verify(dao, times(1)).getUserExperiments(any());
        verify(task, times(1)).addOnCompleteListener(captor.capture());

        captor.getValue().onComplete(task);
        verify(cb, times(1)).callBackResult(any());
    }


    @Test
    public void addTrialTest() {
        ExperimentFSDAO eDAO = mock(ExperimentFSDAO.class, RETURNS_DEEP_STUBS);
        Trial trial = mock(Trial.class, RETURNS_DEEP_STUBS);
        ExperimentHandler handler = new ExperimentHandler(eDAO);

        when(trial.getExperimenter()).thenReturn("1");

        addTrialCallBack cb = mock(addTrialCallBack.class);

        handler.addTrial(trial, cb);

        finishAllTasks();

        verify(trial, times(1)).getExperimentID();
    }
}
