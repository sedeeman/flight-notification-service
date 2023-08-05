package com.sedeeman.ca.flightnotificationservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailNotificationTest {

    @Test
    void testNoArgsConstructor() {
        EmailNotification emailNotification = new EmailNotification();

        assertNull(emailNotification.getTo());
        assertNull(emailNotification.getSubject());
        assertNull(emailNotification.getBody());
    }

    @Test
    void testAllArgsConstructor() {
        String to = "recipient@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        EmailNotification emailNotification = new EmailNotification(to, subject, body);

        assertEquals(to, emailNotification.getTo());
        assertEquals(subject, emailNotification.getSubject());
        assertEquals(body, emailNotification.getBody());
    }

    @Test
    void testSetterGetterMethods() {
        EmailNotification emailNotification = new EmailNotification();

        emailNotification.setTo("new-recipient@example.com");
        emailNotification.setSubject("New Subject");
        emailNotification.setBody("New Body");

        assertEquals("new-recipient@example.com", emailNotification.getTo());
        assertEquals("New Subject", emailNotification.getSubject());
        assertEquals("New Body", emailNotification.getBody());
    }

    @Test
    void testEqualsAndHashCode() {

        EmailNotification emailNotification1 = new EmailNotification("a@example.com", "Subject", "Body");
        EmailNotification emailNotification2 = new EmailNotification("a@example.com", "Subject", "Body");
        EmailNotification emailNotification3 = new EmailNotification("b@example.com", "Subject", "Body");

        assertEquals(emailNotification1, emailNotification2);
        assertNotEquals(emailNotification1, emailNotification3);
        assertEquals(emailNotification1.hashCode(), emailNotification2.hashCode());
        assertNotEquals(emailNotification1.hashCode(), emailNotification3.hashCode());
    }
}
