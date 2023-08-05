package com.sedeeman.ca.flightnotificationservice.service;

import com.sedeeman.ca.flightnotificationservice.dto.EmailNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.AmqpTemplate;
import com.sedeeman.ca.flightnotificationservice.dto.FlightStatusEvent;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

class MessageSendingServiceTest {

    @Mock
    private AmqpTemplate rabbitTemplate;

    @Mock
    private NotificationSubscriptionService notificationSubscriptionService;

    @InjectMocks
    private MessageSendingService messageSendingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOnFlightStatusUpdate() {
        FlightStatusEvent event = new FlightStatusEvent("FL123", "DELAYED");
        List<String> subscriberEmails = Arrays.asList("subscriber1@example.com", "subscriber2@example.com");

        Mockito.when(notificationSubscriptionService.getSubscriberEmailsForFlightStatus(event.getFlightNumber(), event.getStatus()))
                .thenReturn(subscriberEmails);

        messageSendingService.onFlightStatusUpdate(event);

        for (String email : subscriberEmails) {
            EmailNotification expectedEmailNotification = new EmailNotification(email, "Flight Status Update", "Flight " + event.getFlightNumber() + " status: " + event.getStatus());
            Mockito.verify(rabbitTemplate).convertAndSend("flight-status-exchange", "flight-status-routing-key", expectedEmailNotification);
        }
    }
}


