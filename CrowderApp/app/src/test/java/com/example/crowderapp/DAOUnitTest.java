package com.example.crowderapp;

import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.dao.ExperimentDAO;
import com.example.crowderapp.models.dao.ExperimentMockDAO;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DAOUnitTest {

    /**
     * Does basic add and read test on mock dao
     */
    @Test
    public void testMock() {
        ExperimentDAO dao = new ExperimentMockDAO();
        Experiment testExperiment = new Experiment();
        dao.createExperiment(testExperiment);


        Task<List<Experiment>> getTask = dao.getAllExperiments();

        try {
            Tasks.await(getTask);
        } catch (Exception e) {
            // Do nothing if await fails
        }

        Assert.assertEquals(testExperiment, getTask.getResult().get(0));
    }
}
