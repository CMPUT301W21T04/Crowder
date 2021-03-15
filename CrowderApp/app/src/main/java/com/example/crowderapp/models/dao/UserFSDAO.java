package com.example.crowderapp.models.dao;

import android.util.Log;

import com.example.crowderapp.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Firestore implementation for UserDAO
 */
public class UserFSDAO extends UserDAO {

    final private String TAG = "UserFSDAO";

    private FirebaseFirestore db;

    // Firestore collections that has all our users
    private CollectionReference userCollections;

    public UserFSDAO() {
        db = FirebaseFirestore.getInstance();
        userCollections = db.collection("users");
    }

    /**
     * Retrieves a single user by a user ID.
     *
     * @param userId The user's ID
     * @return The user
     */
    @Override
    public Task<User> getUserByID(String userId) {
        return userCollections.document(userId).get().continueWith(task -> {
            return task.getResult().toObject(User.class);
        });
    }

    /**
     * Creates a new user in storage.
     *
     * @param user The user class containing new user info.
     * @return The user ID created in storage.
     */
    @Override
    public Task<String> createUser(User user) {
        return userCollections.add(user).continueWith(task -> {
            // Return the new ID of the user
            return task.getResult().getId();
        }).addOnFailureListener(e -> {
            Log.e(TAG, "createUser: Failed to create user.", e);
        });
    }

    /**
     * Updates user information in storage.
     *
     * @param user User class containing changes to the user.
     */
    @Override
    public void updateUser(User user) {
        userCollections.document(user.getUid()).set(user).addOnFailureListener(e -> {
            Log.e(TAG, "updateUser: Failed to update user with ID: " + user.getUid(), e);
        });
    }
}
