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

import java.util.List;
import java.util.concurrent.TimeUnit;

@Config(sdk = 27)
@RunWith(RobolectricTestRunner.class)
public class ExperimentHandlerUnitTest {

    public void finishAllTasks() {
        ShadowLooper sl = ShadowLooper.shadowMainLooper();
        sl.runToEndOfTasks();
    }

    //@Test
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
}
