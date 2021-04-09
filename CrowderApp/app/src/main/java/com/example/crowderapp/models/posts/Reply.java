package com.example.crowderapp.models.posts;

/**
 * Represents replies to question in comment threads.
 */
public class Reply extends Comment {

    // Question ID of parent
    private String qid;

    /**
     * Creates a full user reply.
     * If commentId needs to be provided, user the setter.
     * @param body The text that the user wants to reply with.
     * @param userId The userId of the user who made the reply.
     * @param qid The parent question ID of the reply.
     */
    public Reply(String body, String userId, String qid) {
        super(body, userId);
        this.qid = qid;
    }

    /**
     * Default constructor.
     * For user in Firebase.
     */
    public Reply() {
    }

    /**
     * The parent question ID of this reply.
     * @return
     */
    public String getQid() {
        return qid;
    }

    /**
     * @param qid Parent QID
     */
    public void setQid(String qid) {
        this.qid = qid;
    }
}
