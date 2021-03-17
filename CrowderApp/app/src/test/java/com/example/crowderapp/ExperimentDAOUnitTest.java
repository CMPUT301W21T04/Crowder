package com.example.crowderapp;

import com.example.crowderapp.models.BinomialExperiment;
import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.MeasurementExperiment;
import com.example.crowderapp.models.MeasurementTrial;
import com.example.crowderapp.models.dao.ExperimentFSDAO;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class ExperimentDAOUnitTest {

    ExperimentFSDAO dao;

    @Mock
    FirebaseFirestore mockDB;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        dao = new ExperimentFSDAO(mockDB);
    }

    @Test
    public void testCreateProperExperiment() {
        DocumentSnapshot doc = mock(DocumentSnapshot.class);

        when(doc.get(same("experimentType"))).thenReturn("Binomial");
        when(doc.toObject(any())).thenReturn(new BinomialExperiment());

        // Make sure this doesn't cause exception.
        BinomialExperiment exp = (BinomialExperiment) dao.createProperExperiment(doc);

        // But this does:
        assertThrows(ClassCastException.class, () -> {
            Experiment exp1 = dao.createProperExperiment(doc);
            MeasurementExperiment mExp = (MeasurementExperiment) exp1;
        });
    }

    @Test
    public void testCreateProperExperimentError() {
        DocumentSnapshot doc = mock(DocumentSnapshot.class);

        when(doc.get(same("experimentType"))).thenReturn("DNE_Experiment");
        assertThrows(IllegalArgumentException.class, () -> {
            dao.createProperExperiment(doc);
        });
    }
}
