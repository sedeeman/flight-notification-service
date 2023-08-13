package com.sedeeman.ca.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@DisplayName("Email Service Tests")
@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    @DisplayName("Test sending email successfully")
    void testSendEmail_Success() {
        String to = "subscriber@email.com";
        String subject = "Test Subject";
        String text = "Test Text";

        emailService.sendEmail(to, subject, text);

        verify(mailSender).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Test sending email with exception")
    void testSendEmailWithException() {
        String to = "subscriber@email.com";
        String subject = "Test Subject";
        String text = "Test Text";

        doThrow(new RuntimeException("Email sending failed")).when(mailSender).send(any(SimpleMailMessage.class));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            emailService.sendEmail(to, subject, text);
        });

        assertEquals("Email sending failed", exception.getMessage());

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
