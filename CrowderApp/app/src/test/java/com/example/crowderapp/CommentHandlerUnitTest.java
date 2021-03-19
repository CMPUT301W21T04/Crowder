package com.example.crowderapp;

import com.example.crowderapp.controllers.CommentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.addQuestionToExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentQuestionsCallBack;
import com.example.crowderapp.models.dao.CommentFSDAO;
import com.example.crowderapp.models.posts.Comment;
import com.example.crowderapp.models.posts.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.mockito.ArgumentCaptor;
import org.robolectric.shadows.ShadowLooper;

import java.util.List;

import static org.mockito.Mockito.*;

@Config(sdk = 27)
@RunWith(RobolectricTestRunner.class)
public class CommentHandlerUnitTest {

    // Enables callbacks to complete before ending test
    public void finishAllTasks() {
        ShadowLooper sl = ShadowLooper.shadowMainLooper();
        sl.runToEndOfTasks();
    }

    @Test
    public void getExperimentQuestionsTest () {
        CommentFSDAO dao = mock(CommentFSDAO.class, RETURNS_DEEP_STUBS);
        Task<List<Question>> task = mock(Task.class, RETURNS_DEEP_STUBS);
        CommentHandler handler = new CommentHandler(dao);

        when(dao.getQuestionsForExperiment(any())).thenReturn(task);

        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);
        getExperimentQuestionsCallBack mockedCB = mock(getExperimentQuestionsCallBack.class);

        handler.getExperimentQuestions(any(), mockedCB);

        finishAllTasks();

        verify(dao, times(1)).getQuestionsForExperiment(any());
        verify(task, times(1)).addOnCompleteListener(captor.capture());
        when(task.isSuccessful()).thenReturn(true);
        captor.getValue().onComplete(task);

        verify(mockedCB, times(1)).callBackResult(any());

    }

    @Test
    public void addQuestionToExperimentTest () {
        CommentFSDAO dao = mock(CommentFSDAO.class, RETURNS_DEEP_STUBS);
        Task<String> task = mock(Task.class, RETURNS_DEEP_STUBS);
        CommentHandler handler = new CommentHandler(dao);

        when(dao.addQuestionForExperiment(any(), any())).thenReturn(task);

        ArgumentCaptor<OnCompleteListener> captor = ArgumentCaptor.forClass(OnCompleteListener.class);
        addQuestionToExperimentCallBack mockedCB = mock(addQuestionToExperimentCallBack.class);

        handler.addQuestionToExperiment(any(), any(), mockedCB);

        finishAllTasks();

        verify(dao, times(1)).addQuestionForExperiment(any(), any());
        verify(task, times(1)).addOnCompleteListener(captor.capture());
        when(task.isSuccessful()).thenReturn(true);
        captor.getValue().onComplete(task);

        verify(mockedCB, times(1)).callBackResult(any());

    }




}
