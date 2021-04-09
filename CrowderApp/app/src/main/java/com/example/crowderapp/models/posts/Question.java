package com.example.crowderapp.models.posts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Question class that specifically represents head of
 * heads in comments.
 */
public class Question extends Comment {
    private List<Reply> replyList;

    /**
     * Default constructor for user in firebase.
     */
    public Question() {
        super();
        replyList = new ArrayList<>();
    }

    /**
     * Construct a full question.
     * If commentId needs to be provided, user the setter.
     * @param body The question text that the user is asking.
     * @param userId The userID of the user who asked the question.
     */
    public Question(String body, String userId) {
        super(body, userId);
        replyList = new ArrayList<>();
    }

    /**
     * Adds a new reply answering this question to the list.
     * @param r Reply to add.
     */
    public void addReply(Reply r) {
        r.setQid(commentId); // Set ID if not already
        replyList.add(r);
    }

    /**
     * The list of all replies that answer this question.
     * @return
     */
    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }
}
