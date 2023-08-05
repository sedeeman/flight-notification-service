package com.sedeeman.ca.flightnotificationservice.service;

import com.sedeeman.ca.flightnotificationservice.dto.EmailNotification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.mail.MailAuthenticationException;

class MessageConsumingServiceTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private MessageConsumingService messageConsumingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testProcessEmailNotification_Success() {
        EmailNotification emailNotification = new EmailNotification("recipient@example.com", "Test Subject", "Test Body");

        Mockito.doNothing().when(emailService).sendEmail(emailNotification.getTo(), emailNotification.getSubject(), emailNotification.getBody());
        messageConsumingService.processEmailNotification(emailNotification);

        Mockito.verify(emailService).sendEmail(emailNotification.getTo(), emailNotification.getSubject(), emailNotification.getBody());
    }

    @Test
    void testProcessEmailNotification_MailException() {
        EmailNotification emailNotification = new EmailNotification("recipient@example.com", "Test Subject", "Test Body");

        Mockito.doThrow(new MailAuthenticationException("Authentication failed")).when(emailService).sendEmail(emailNotification.getTo(), emailNotification.getSubject(), emailNotification.getBody());

        Assertions.assertThrows(AmqpRejectAndDontRequeueException.class, () -> messageConsumingService.processEmailNotification(emailNotification));
    }

    @Test
    void testProcessEmailNotification_UnexpectedError() {
        EmailNotification emailNotification = new EmailNotification("recipient@example.com", "Test Subject", "Test Body");

        Mockito.doThrow(new RuntimeException("Unexpected error occurred")).when(emailService).sendEmail(emailNotification.getTo(), emailNotification.getSubject(), emailNotification.getBody());

        Assertions.assertThrows(AmqpRejectAndDontRequeueException.class, () -> messageConsumingService.processEmailNotification(emailNotification));
    }

}
