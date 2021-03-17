package com.example.crowderapp;

import android.app.Activity;
import android.content.SharedPreferences;

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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowLooper;

import java.util.concurrent.CountDownLatch;

@Config(sdk = 27)
@RunWith(RobolectricTestRunner.class)
public class UserUnitTest {

    UserHandler handler;
    SharedPreferences mockedPref;
    UserDAO mockedDao;
    User testUser;

    @Before
    public void setupHandler() {
        mockedPref = Mockito.mock(SharedPreferences.class, Mockito.RETURNS_DEEP_STUBS);
        mockedDao = Mockito.mock(UserFSDAO.class, Mockito.RETURNS_DEEP_STUBS);

        testUser = new User();
        testUser.setUid("TEST");

        Mockito.when(mockedDao.createUser(Mockito.any()))
                .thenReturn(Tasks.forResult("TEST"));

        Mockito.clearInvocations(mockedDao, mockedPref);
    }

    public void finishAllTasks() {
        ShadowLooper sl = ShadowLooper.shadowMainLooper();
        sl.runToEndOfTasks();
    }

    @Test
    public void testCreateNewUser() {
        handler = new UserHandler(mockedPref, mockedDao);
        Task<User> user = handler.getCurrentUser();

        finishAllTasks();

        // Create user is called
        Mockito.verify(mockedDao, Mockito.times(1)).createUser(Mockito.any());

        Assert.assertEquals("TEST", user.getResult().getUid());
    }

    @Test
    public void testExistingUser() {
        User testUser = new User();
        testUser.setUid("TEST");

        //  Stub some pref commands
        Mockito.when(mockedPref.getString(Mockito.any(), Mockito.same(null))).thenReturn("TEST");
        Mockito.when(mockedDao.getUserByID(Mockito.any()))
                .thenReturn(Tasks.forResult(testUser));

        handler = new UserHandler(mockedPref, mockedDao);

        // User should come from prefs now
        Task<User> user = handler.getCurrentUser();

        finishAllTasks();

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

        handler.observeCurrentUser(mockedActivity, (dao, user) -> {
            // Do nothing
        });

        finishAllTasks();

        Mockito.verify(mockedDao, Mockito.times(1)).observeUser(Mockito.same("TEST"), Mockito.same(mockedActivity), Mockito.any());
    }
}
