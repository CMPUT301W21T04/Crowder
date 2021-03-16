package com.example.crowderapp;

import android.app.Activity;
import android.content.SharedPreferences;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.work.Worker;

import com.example.crowderapp.controllers.UserHandler;
import com.example.crowderapp.models.User;
import com.example.crowderapp.models.dao.UserDAO;
import com.example.crowderapp.models.dao.UserFSDAO;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.common.util.concurrent.AbstractScheduledService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.concurrent.CountDownLatch;

public class UserUnitTest {

    UserHandler handler;
    SharedPreferences mockedPref;
    UserDAO mockedDao;
    User testUser;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setupHandler() {
        mockedPref = Mockito.mock(SharedPreferences.class);
        mockedDao = Mockito.mock(UserFSDAO.class, Mockito.RETURNS_DEEP_STUBS);

        testUser = new User();
        testUser.setUid("TEST");

        Mockito.when(mockedDao.createUser(Mockito.any()).continueWith(Mockito.any()))
                .thenReturn(Tasks.forResult(testUser));

        Mockito.when(mockedDao.getUserByID(Mockito.any()).continueWith(Mockito.any()))
                .thenReturn(Tasks.forResult(testUser));

        Mockito.clearInvocations(mockedDao, mockedPref);
    }


    @Test
    public void testCreateNewUser() {
        handler = new UserHandler(mockedPref, mockedDao);
        Task<User> user = handler.getCurrentUser();

        // Create user is called
        Mockito.verify(mockedDao, Mockito.times(1)).createUser(Mockito.any());

        Assert.assertEquals(testUser, user.getResult());
    }

    @Test
    public void testExistingUser() {
        //  Stub some pref commands
        Mockito.when(mockedPref.getString(Mockito.any(), Mockito.same(null))).thenReturn("TEST");

        handler = new UserHandler(mockedPref, mockedDao);

        // User should come from prefs now
        Task<User> user = handler.getCurrentUser();

        // Get user is called.
        Mockito.verify(mockedDao, Mockito.times(1)).getUserByID(Mockito.any());
        // But not create user
        Mockito.verify(mockedDao, Mockito.times(0)).createUser(Mockito.any());
        Assert.assertEquals("TEST", user.getResult().getUid());
    }

    @Test
    public void testObserve() throws Exception {
        handler = new UserHandler(mockedPref, mockedDao);
        Activity mockedActivity = Mockito.mock(Activity.class);

        handler.syncObserverCurrentUser(testUser, mockedActivity, (dao, user) -> {
            // Do nothing
        });

        Mockito.verify(mockedDao, Mockito.times(1)).observeUser(Mockito.same("TEST"), Mockito.same(mockedActivity), Mockito.any());
    }
}
