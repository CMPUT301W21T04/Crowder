package com.example.crowderapp.models.posts;

import com.google.firebase.firestore.DocumentId;

/**
 * Comment class that represents the user posts in the
 * question-answer posts.
 */
public abstract class Comment {
    String body;
    String username;

    @DocumentId //
    String commendId;

    /**
     * Default constructor.
     * Used by Firestore.
     */
    public Comment() {
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
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
