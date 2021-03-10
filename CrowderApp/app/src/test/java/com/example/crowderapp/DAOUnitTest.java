package com.example.crowderapp;

import com.example.crowderapp.models.BinomialExperiment;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.dao.ExperimentDAO;
import com.example.crowderapp.models.dao.ExperimentMockDAO;
import com.example.crowderapp.models.dao.TrialDAO;
import com.example.crowderapp.models.dao.TrialMockDAO;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DAOUnitTest {

    /**
     * Does basic add and read test on mock dao
     */
    @Test
    public void testMockExperiment() {
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

    @Test
    public void testMockTrial() {
        ExperimentDAO dao = new ExperimentMockDAO();
        Experiment exp = new BinomialExperiment();
        Task<String> idTask = dao.createExperiment(exp);

        try {
            Tasks.await(idTask);
        } catch (Exception e) {
            // Do nothing if await fails
        }

        exp.setExperimentID(idTask.getResult());

        TrialDAO daoT = new TrialMockDAO(exp);
        Trial trial = new BinomialTrial("Me", new Date(), false);
        daoT.addExperimentTrial(trial);

        Task<List<Trial>> trialTask = daoT.getExperimentTrials();

        try {
            Tasks.await(trialTask);
        } catch (Exception e) {
            // Do nothing if await fails
        }

        Assert.assertEquals(trial, trialTask.getResult().get(0));
    }
}
