package com.example.crowderapp;

import com.example.crowderapp.models.BinomialTrial;
import com.example.crowderapp.models.CounterTrial;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.Trial;
import com.example.crowderapp.models.dao.TrialFSDAO;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TrialDAOUnitTest {


    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    FirebaseFirestore mockDB;
    
    @Mock
    Experiment mockExp;

    TrialFSDAO dao;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        when(mockExp.getExperimentType()).thenReturn("Binomial");
        when(mockExp.getExperimentID()).thenReturn("ABC");
        
        dao = new TrialFSDAO(mockExp, mockDB);
    }

    @Test
    public void testCreateProperTrial() {
        DocumentSnapshot mockDoc = mock(DocumentSnapshot.class);

        when(mockDoc.toObject(any())).thenReturn(new BinomialTrial());

        Trial trial = dao.getProperTrial(mockDoc);

        // Can be downcasted.
        BinomialTrial b = (BinomialTrial) trial;

        // But not to wrong trial
        assertThrows(ClassCastException.class, () -> {
            CounterTrial c = (CounterTrial) trial;
        });
    }
}
