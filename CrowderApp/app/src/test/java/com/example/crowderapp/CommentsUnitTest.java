package com.example.crowderapp;

import com.example.crowderapp.models.posts.Question;
import com.example.crowderapp.models.posts.Reply;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

/**
 * Unit test for question and replies classes.
 */
public class CommentsUnitTest {

    Question question;

    public Reply mockReply(String body) {
        Reply r = new Reply();
        r.setBody(body);
        r.setUsername("TestUser1");
        return r;
    }

    @Before
    public void setUp() throws Exception {
        question = new Question();
        question.setBody("I am a question.");
        question.setUsername("TestUser");
    }

    /**
     * Replies form a thread so they must remain ordered when adding.
     */
    @Test
    public void testOrderedReplies() {
        question.addReply(mockReply("1"));
        question.addReply(mockReply("2"));
        question.addReply(mockReply("3"));

        Assert.assertEquals("1", question.getReplyList().get(0).getBody());
        Assert.assertEquals("2", question.getReplyList().get(1).getBody());
        Assert.assertEquals("3", question.getReplyList().get(2).getBody());

    }
}
