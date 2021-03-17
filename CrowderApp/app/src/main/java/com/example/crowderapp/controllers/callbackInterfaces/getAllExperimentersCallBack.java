package com.example.crowderapp.controllers.callbackInterfaces;

import com.example.crowderapp.models.User;

import java.util.List;

public interface getAllExperimentersCallBack {
    public void callBackResult(List<User> userList);
}
