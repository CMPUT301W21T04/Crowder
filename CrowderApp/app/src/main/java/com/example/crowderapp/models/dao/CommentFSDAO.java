package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.posts.Question;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Concrete Implementation of CommentDAO that uses the Firestore Database.
 *
 * Structure:
 *
 * Collection: Experiments
 *  - Document: ExperimentId
 *      Collection: Questions
 *          - Document: QuestionId
 */
public class CommentFSDAO extends CommentDAO {

    private FirebaseFirestore db;

    private CollectionReference commentsReference;

    /**
     * For user dependency injection in tests.
     * @param db
     */
    public CommentFSDAO(FirebaseFirestore db) {
        this.db = db;
        commentsReference = db.collection("experiment");
    }

    /**
     * Preffered constructor for using this dao.
     */
    public CommentFSDAO() {
        this(FirebaseFirestore.getInstance());
    }

    private CollectionReference getExperimentCommentsColl(String expId) {
        return commentsReference.document(expId).collection("Comments");
    }

    /**
     * Gets the list of all questions for an experiment.
     * Each question head will have a list containing all its comments.
     * @param expId The experiment id which to get comments for.
     * @return Question List
     */
    @Override
    public Task<List<Question>> getQuestionsForExperiment(String expId) {
        return getExperimentCommentsColl(expId).get().continueWith(task -> {
            return task.getResult().toObjects(Question.class);
        });
    }

    /**
     * Creates a new question thread for experiment in persistent storage.
     *
     * @param expId    The experiment which to create a question for.
     * @param question The question containing question info and all replies.
     * @return The question thread ID.
     */
    @Override
    public Task<String> addQuestionForExperiment(String expId, Question question) {
        return getExperimentCommentsColl(expId).add(question).continueWith(task -> {
            return task.getResult().getId();
        });
    }

    /**
     * Update the question thread for experiment in persistent storage.
     *
     * @param expId    The experiment ID which will have its questions updated.
     * @param question The question containing question info and all replies.
     */
    @Override
    public void updateQuestionForExperiment(String expId, Question question) {
        getExperimentCommentsColl(expId).document(question.getCommentId()).set(question);
    }
}
