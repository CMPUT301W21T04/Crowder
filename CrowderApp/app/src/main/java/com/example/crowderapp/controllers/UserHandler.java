package com.example.crowderapp.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.crowderapp.models.User;
import com.example.crowderapp.models.dao.UserDAO;
import com.example.crowderapp.models.dao.UserFSDAO;
import com.google.android.gms.tasks.Task;

/**
 * Controller class to handle all things about users, whether
 * current or on other devices.
 */
public class UserHandler {

    final private String TAG = "UserHandler";

    // Key for shared preferences file
    static final public String USER_DATA_KEY = "USER_DATA";

    // Key for items in shared preferences
    final private String USER_ID_KEY = "USER_DATA_ID";

    private UserDAO userDAO;
    private SharedPreferences sharedPreferences;

    private Task<User> currentUserTask;

    /**
     * Constructor.
     * Creates a new user profile if a user ID cannot be found in the shared preferences.
     * @param pref Shared preferences from activity
     */
    public UserHandler(SharedPreferences pref) {
        this(pref, new UserFSDAO());
    }

    public UserHandler(SharedPreferences pref, UserDAO dao) {
        sharedPreferences = pref;
        userDAO = dao;

        String userId = sharedPreferences.getString(USER_ID_KEY, null);

        if (userId == null) {
            // Need to create new user id for new app user

            User newUser = new User();
            currentUserTask = userDAO.createUser(newUser).continueWith(task -> {
                // User name will also be ID
                newUser.setName(task.getResult());

                newUser.setUid(task.getResult());

                return newUser;
            });

        } else {
            // Returning user
            currentUserTask = getUserByID(userId);
        }
    }

    /**
     * Gets the user tied to the phone.
     * @return The user
     */
    public  Task<User> getCurrentUser() {
        return currentUserTask;
    }

    /**
     * Gets a specific user by their ID.
     * @param userId
     * @return
     */
    public Task<User> getUserByID(String userId) {
        return userDAO.getUserByID(userId).continueWith(task -> {
            return task.getResult();
        });
    }

    /**
     * Updates the information of the current user.
     * @param user
     */
    public void updateCurrentUser(User user) {

        currentUserTask.addOnSuccessListener(currentUser -> {
            if (user.getUid() != currentUser.getUid()) { // Programmer error
                throw new RuntimeException("Cannot update non-current user.");
            }
            userDAO.updateUser(user);
        });


    }

    /**
     * Adds an experiment (by ID) to the user's subscription.
     * @param experimentID
     */
    public void subscribeExperiment(String experimentID) {
        currentUserTask.addOnSuccessListener(user -> {
            user.getSubscribedExperiments().add(experimentID);
            updateCurrentUser(user);
        });

    }
}
