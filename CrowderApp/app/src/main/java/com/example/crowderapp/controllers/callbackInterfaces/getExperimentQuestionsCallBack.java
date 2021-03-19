package com.example.crowderapp.controllers.callbackInterfaces;

import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.posts.Question;

import java.util.List;

public interface getExperimentQuestionsCallBack {
    public void callBackResult(List<Question> questionList);
}
