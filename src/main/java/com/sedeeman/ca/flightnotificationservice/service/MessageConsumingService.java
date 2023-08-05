package com.sedeeman.ca.flightnotificationservice.service;

import com.sedeeman.ca.flightnotificationservice.dto.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;


@Component
public class MessageConsumingService {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumingService.class);
    private final EmailService emailService;

    @Autowired
    public MessageConsumingService(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "email-queue")
    public void processEmailNotification(EmailNotification emailNotification) {
        try {
            emailService.sendEmail(emailNotification.getTo(), emailNotification.getSubject(), emailNotification.getBody());
            logger.info("Email sent successfully to: {}", emailNotification.getTo());

        } catch (MailException e) {
            throw new AmqpRejectAndDontRequeueException("Failed to process email notification", e);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Unexpected error occurred while processing email notification", e);
        }
    }


}
