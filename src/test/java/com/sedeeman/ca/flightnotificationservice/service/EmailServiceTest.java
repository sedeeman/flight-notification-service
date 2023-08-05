package com.sedeeman.ca.flightnotificationservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSendEmail() {

        String to = "recipient@example.com";
        String subject = "Test Email";
        String text = "This is a test email message.";

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(text);

        emailService.sendEmail(to, subject, text);

        Mockito.verify(mailSender).send(expectedMessage);
    }
}
