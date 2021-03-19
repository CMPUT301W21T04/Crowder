package com.example.crowderapp.models.dao;

import com.example.crowderapp.models.posts.Question;
import com.google.android.gms.tasks.Task;

import java.util.List;

/**
 * Abstract Class for a Comment Data Access Object.
 * Used to represent persistent storage whether it be DB, filesystem, etc.
 */
abstract public class CommentDAO {

    /**
     * Gets the list of all questions for an experiment.
     * Each question head will have a list containing all its comments.
     * @param expId The experiment id which to get comments for.
     * @return Question List
     */
    public abstract Task<List<Question>> getQuestionsForExperiment(String expId);

    /**
     * Creates a new question thread for experiment in persistent storage.
     * @param expId The experiment which to create a question for.
     * @param question The question containing question info and all replies.
     * @return The question thread ID.
     */
    public abstract Task<String> addQuestionForExperiment(String expId, Question question);

    /**
     * Update the question thread for experiment in persistent storage.
     * @param expId The experiment ID which will have its questions updated.
     * @param question The question containing question info and all replies.
     */
    public abstract void updateQuestionForExperiment(String expId, Question question);
}
