package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.User;
import com.google.android.gms.tasks.Task;

public abstract class UserDAO {

    /**
     * Retrieves a single user by a user ID.
     * @param userId The user's ID
     * @return The user
     */
    public abstract Task<User> getUserByID(String userId);

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
