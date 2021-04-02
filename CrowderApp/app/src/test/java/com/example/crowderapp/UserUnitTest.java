package com.example.crowderapp;

import com.example.crowderapp.models.User;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserUnitTest {

    @Test
    public void testPhoneValdiation() {
        assertTrue(User.validPhoneNumber("780 111 2222"));
        assertTrue(User.validPhoneNumber("780-111-2222"));
        assertFalse(User.validPhoneNumber("7801112222"));
    }

    @Test
    public void testEmailValidation() {
        assertTrue(User.validEmail("test.user@email.com"));
        assertFalse(User.validEmail("Not an email."));
    }
}
