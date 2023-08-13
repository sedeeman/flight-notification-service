package com.sedeeman.ca.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SubscriptionException Tests")
class SubscriptionExceptionTest {

    @Test
    @DisplayName("Test constructor with message")
    void testConstructorWithMessage() {
        String message = "Test exception message";
        SubscriptionException exception = new SubscriptionException(message);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Test constructor with message and cause")
    void testConstructorWithMessageAndCause() {
        String message = "Test exception message";
        Throwable cause = new RuntimeException("Test cause");
        SubscriptionException exception = new SubscriptionException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
