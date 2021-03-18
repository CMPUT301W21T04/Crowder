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
     * Default conzstructor for user in firebase.
     */
    public Question() {
        super();
        replyList = new ArrayList<>();
    }

    /**
     * Adds a new reply answering this question to the list.
     * @param r Reply to add.
     */
    public void addReply(Reply r) {
        r.setQid(commendId); // Set ID if not already
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
