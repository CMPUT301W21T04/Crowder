package com.example.crowderapp.models.posts;

import com.google.firebase.firestore.DocumentId;

/**
 * Comment class that represents the user posts in the
 * question-answer posts.
 */
public abstract class Comment {
    String body;
    String userId;

    @DocumentId //
    String commendId;

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
    public String getCommendId() {
        return commendId;
    }

    public void setCommendId(String commendId) {
        this.commendId = commendId;
    }
}
