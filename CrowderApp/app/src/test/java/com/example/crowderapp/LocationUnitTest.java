package com.example.crowderapp;


import android.content.Context;

import com.example.crowderapp.controllers.LocationHandler;
import com.example.crowderapp.models.Location;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLooper;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@Config(sdk = 28)
@RunWith(RobolectricTestRunner.class)
public class LocationUnitTest {
    @Mock
    FusedLocationProviderClient fusedProvider;

    Task<android.location.Location> successTask;
    Task<android.location.Location> failTask;

    LocationHandler handler;

    public void finishAllTasks() {
        ShadowLooper sl = ShadowLooper.shadowMainLooper();
        sl.runToEndOfTasks();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        TaskCompletionSource<android.location.Location> successSource = new TaskCompletionSource<>();
        successSource.setResult(new android.location.Location(""));
        successTask = successSource.getTask();

        TaskCompletionSource<android.location.Location> failSource = new TaskCompletionSource<>();
        failSource.setException(new SecurityException());
        failTask = failSource.getTask();

        handler = new LocationHandler(mock(Context.class), fusedProvider);
    }

    @Test
    public void hasPermissionGetLocation() {
        when(fusedProvider.getLastLocation()).thenReturn(successTask);
        handler.getCurrentLocation(location -> {
            assertEquals(location.getLatitude(), 0, 0);
            assertEquals(location.getLongitude(), 0, 0);
        });

        finishAllTasks();
    }

    @Test
    public void noPermissionGetLocation() {
        when(fusedProvider.getLastLocation()).thenReturn(failTask);

        // Must throw it and not just silently cover it up
        assertThrows(SecurityException.class, () -> {
            handler.getCurrentLocation(location -> {});
            finishAllTasks();
        });
    }
}
