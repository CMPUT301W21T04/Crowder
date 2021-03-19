package com.example.crowderapp.controllers;

import androidx.annotation.NonNull;

import com.example.crowderapp.controllers.callbackInterfaces.addQuestionToExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentQuestionsCallBack;
import com.example.crowderapp.models.dao.CommentFSDAO;
import com.example.crowderapp.models.posts.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.logging.Logger;

public class CommentHandler {

    private CommentFSDAO dao;
    private Logger logger;

    public CommentHandler() {
        dao = new CommentFSDAO();
        logger = Logger.getLogger(ExperimentHandler.class.getName());
    }

    public CommentHandler(CommentFSDAO dao) {
        this.dao = dao;
        logger = Logger.getLogger(ExperimentHandler.class.getName());
    }

    public void getExperimentQuestions(String experimentID, getExperimentQuestionsCallBack callback) {
        Task<List<Question>> task = dao.getQuestionsForExperiment(experimentID);

        task.addOnCompleteListener(new OnCompleteListener<List<Question>>() {
            @Override
            public void onComplete(@NonNull Task<List<Question>> task) {
                if (task.isSuccessful()) {
                    callback.callBackResult(task.getResult());
                }
            }
        });

    }

    public void addQuestionToExperiment(String experimentID, Question question, addQuestionToExperimentCallBack callback) {
        Task<String> task = dao.addQuestionForExperiment(experimentID, question);

        task.addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()) {
                    callback.callBackResult(task.getResult());
                }
            }
        });

    }

    public void updateQuestionForExperiment(String experimentID, Question question) {
        dao.updateQuestionForExperiment(experimentID, question);
    }



}
