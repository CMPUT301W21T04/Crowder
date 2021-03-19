package com.example.crowderapp.models.dao;

import android.app.Activity;

import com.example.crowderapp.models.User;
import com.google.android.gms.tasks.Task;

import java.util.Observer;

public abstract class UserDAO {

    /**
     * Observer for a user.
     */
    public interface UserObserver {
        void update(UserDAO dao, User user);
    }

    /**
     * Retrieves a single user by a user ID.
     * @param userId The user's ID
     * @return The user
     */
    public abstract Task<User> getUserByID(String userId);

    /**
     * Observes a user in storage.
     * @param userId The user to observe.
     * @param observer Callback to receive user.
     */
    public abstract void observeUser(String userId, Activity activity, UserObserver observer);

    /**
     * Creates a new user in storage.
     * @param user The user class containing new user info.
     * @return The user ID created in storage.
     */
    public abstract Task<String> createUser(User user);

    /**
     * Updates user information in storage.
     * @param user User class containing changes to the user.
     */
    public abstract void updateUser(User user);
}
