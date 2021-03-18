package com.example.crowderapp.models.posts;

/**
 * Represents replies to question in comment threads.
 */
public class Reply extends Comment {

    private String qid;

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

    public void setQid(String qid) {
        this.qid = qid;
    }
}
