package com.example.crowderapp.models.dao;

import android.app.Activity;
import android.util.Log;

import com.example.crowderapp.models.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Concrete Firestore implementation for UserDAO
 */
public class UserFSDAO extends UserDAO {

    final private String TAG = "UserFSDAO";
    private FirebaseFirestore db;
    private List<Observer> observerList;

    // Firestore collections that has all our users
    private CollectionReference userCollections;

    public UserFSDAO() {
        db = FirebaseFirestore.getInstance();
        userCollections = db.collection("users");

        observerList = new ArrayList<>();
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
     * Retrieves multiple users by their ids.
     *
     * @param userIds The ids
     * @return Task for the list
     */
    @Override
    public Task<List<User>> getUserListById(List<String> userIds) {
        if(userIds.size() == 0) {
            List<User> emptyListOfUsers = new ArrayList<>();
            TaskCompletionSource<List<User>> emptyTaskSource = new TaskCompletionSource<>();
            emptyTaskSource.setResult(emptyListOfUsers);
            return emptyTaskSource.getTask();
        }
        else {
            return userCollections.whereIn(FieldPath.documentId(), userIds).get().continueWith(task -> {
                return task.getResult().toObjects(User.class);
            });
        }
    }

    /**
     * Gets all the users.
     *
     * @return Task with list of all users.
     */
    @Override
    public Task<List<User>> getAllUsers() {
        return userCollections.get().continueWith(task -> {
            return task.getResult().toObjects(User.class);
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

    /**
     * Observe a user in Firestore.
     * @param userId The user to observe.
     * @param activity The activity observing. Use to prevent leaks.
     * @param observer Callback to receive user.
     */
    @Override
    public void observeUser(String userId, Activity activity, UserObserver observer) {
        userCollections.document(userId).addSnapshotListener(activity, (value, error) -> {
            User user = value.toObject(User.class);
            observer.update(this, user);
        });
    }

    /**
     * Updates users in bulk in storage.
     *
     * @param users The list of users to update.
     * @return Task for when transaction is done.
     */
    @Override
    public Task<Void> bulkUpdateUser(List<User> users) {
        WriteBatch batch = db.batch();

        for (User user : users) {
            DocumentReference userRef = userCollections.document(user.getUid());
            batch.set(userRef, user);
        }

        return batch.commit().addOnFailureListener(e -> {
            Log.e(TAG, "bulkUpdateUser: Failed to update bulk users.", e);
        });
    }
}
