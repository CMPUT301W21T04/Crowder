package com.example.crowderapp.controllers;

import androidx.annotation.NonNull;

import com.example.crowderapp.controllers.callbackInterfaces.addQuestionToExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.addReplyToQuestionCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentQuestionsCallBack;
import com.example.crowderapp.models.dao.CommentFSDAO;
import com.example.crowderapp.models.posts.Question;
import com.example.crowderapp.models.posts.Reply;
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

    /**
     * an injectable constructor for testing
     * @param dao : the test dao
     */
    public CommentHandler(CommentFSDAO dao) {
        this.dao = dao;
        logger = Logger.getLogger(ExperimentHandler.class.getName());
    }

    /**
     * grabs the questions for an experiment
     * @param experimentID : the experiment ID
     * @param callback : the callback interface after the async call finishes
     */
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

    /**
     * adds a question to an experiment
     * @param experimentID : the experiment ID
     * @param question : the question to be added
     * @param callback : the callback interface after the async call finishes
     */
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

    /**
     * updates the question for an experiment
     * @param experimentID : the experiment ID
     * @param question : the question that is to be updated
     */
    public void updateQuestionForExperiment(String experimentID, Question question) {
        dao.updateQuestionForExperiment(experimentID, question);
    }
    
}
