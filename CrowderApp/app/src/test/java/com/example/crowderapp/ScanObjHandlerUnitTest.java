package com.example.crowderapp;

import com.example.crowderapp.controllers.ScanObjHandler;
import com.example.crowderapp.models.ScanObj;
import com.example.crowderapp.models.dao.ScanObjectFSDAO;
import com.google.android.gms.tasks.TaskCompletionSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@Config(sdk = 27)
@RunWith(RobolectricTestRunner.class)
public class ScanObjHandlerUnitTest {
    @Mock
    ScanObjectFSDAO dao;

    ScanObjHandler handler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        handler = new ScanObjHandler("Test", dao);
    }

    public void finishAllTasks() {
        ShadowLooper sl = ShadowLooper.shadowMainLooper();
        sl.runToEndOfTasks();
    }

    @Test
    public void generateIdTest() {
        TaskCompletionSource<String> ts = new TaskCompletionSource();
        ts.setResult("ID");
        when(dao.createUpdateScanObj(any())).thenReturn(ts.getTask());
        handler.createScanObj("Test", o -> {
            assertEquals("ID", o.getKey());
        });
        finishAllTasks();
    }

    @Test
    public void noGenIdTest() {
        TaskCompletionSource<String> ts = new TaskCompletionSource();
        ts.setResult(""); // Can return anything
        when(dao.createUpdateScanObj(any())).thenReturn(ts.getTask());

        handler.createScanObj("Test", "Test", o -> {
            assertEquals("Test", o.getKey());
        });
        finishAllTasks();

        ArgumentCaptor<ScanObj> captor = ArgumentCaptor.forClass(ScanObj.class);
        verify(dao).createUpdateScanObj(captor.capture());

        assertEquals("Test", captor.getValue().getKey());
    }
}
