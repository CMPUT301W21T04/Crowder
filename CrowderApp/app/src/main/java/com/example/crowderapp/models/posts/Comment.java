package com.example.crowderapp.models.posts;

import com.google.firebase.firestore.DocumentId;

import java.io.Serializable;

/**
 * Comment class that represents the user posts in the
 * question-answer posts.
 */
public abstract class Comment implements Serializable {
    String body;
    String userId;

    @DocumentId //
    String commentId;

    /**
     * Default constructor.
     * Used by Firestore.
     */
    public Comment() {
    }

    public Comment(String body, String userId) {
        this.body = body;
        this.userId = userId;
    }

    /**
     * @return The body of the comment in the question-answer posts.
     */
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return The user who created this comment.
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return Database ID for this comment.
     */
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commendId) {
        this.commentId = commendId;
    }
}
