package com.sedeeman.ca.flightnotificationservice.service;


import com.sedeeman.ca.flightnotificationservice.dto.EmailNotification;
import com.sedeeman.ca.flightnotificationservice.dto.FlightStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageSendingService {

    private static final Logger logger = LoggerFactory.getLogger(MessageSendingService.class);
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private NotificationSubscriptionService notificationSubscriptionService;

    public void onFlightStatusUpdate(FlightStatusEvent event) {
        String flightNumber = event.getFlightNumber();
        String status = event.getStatus();

        // Fetch subscribers from the database
        List<String> subscriberEmails = notificationSubscriptionService.getSubscriberEmailsForFlightStatus(flightNumber, status);

        logger.info("Flight status updated with message service RabbitMQ");

        for (String email : subscriberEmails) {
            EmailNotification emailNotification = new EmailNotification(email, "Flight Status Update", "Flight " + flightNumber + " status: " + status);
            rabbitTemplate.convertAndSend("flight-status-exchange", "flight-status-routing-key", emailNotification);
        }
    }

}
