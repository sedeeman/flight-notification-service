package com.sedeeman.ca.flightnotificationservice.service;

import com.sedeeman.ca.flightnotificationservice.dto.NotificationSubscriptionRequest;
import com.sedeeman.ca.flightnotificationservice.model.NotificationSubscription;
import com.sedeeman.ca.flightnotificationservice.repository.NotificationSubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class NotificationSubscriptionServiceTest {

    @Mock
    private NotificationSubscriptionRepository notificationSubscriptionRepository;

    @InjectMocks
    private NotificationSubscriptionService notificationSubscriptionService;

    private static final Logger logger = LoggerFactory.getLogger(NotificationSubscriptionService.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveSubscription_Success() {
        NotificationSubscriptionRequest subscriptionReq = new NotificationSubscriptionRequest();
        subscriptionReq.setEmail("subscriber@example.com");
        subscriptionReq.setFlightNumber("FL123");
        subscriptionReq.setSubscribeToDelay(true);
        subscriptionReq.setSubscribeToArrival(false);
        subscriptionReq.setSubscribeToDeparture(true);

        NotificationSubscription savedSubscription = new NotificationSubscription();
        savedSubscription.setId(1L);
        savedSubscription.setEmail(subscriptionReq.getEmail());
        savedSubscription.setFlightNumber(subscriptionReq.getFlightNumber());
        savedSubscription.setSubscribeToDelay(subscriptionReq.isSubscribeToDelay());
        savedSubscription.setSubscribeToArrival(subscriptionReq.isSubscribeToArrival());
        savedSubscription.setSubscribeToDeparture(subscriptionReq.isSubscribeToDeparture());

        Mockito.when(notificationSubscriptionRepository.save(any(NotificationSubscription.class))).thenReturn(savedSubscription);

        NotificationSubscription result = notificationSubscriptionService.saveSubscription(subscriptionReq);

        assertEquals(savedSubscription, result);
        Mockito.verify(notificationSubscriptionRepository).save(any(NotificationSubscription.class));
    }

    @Test
    void testSaveSubscription_Exception() {
        NotificationSubscriptionRequest subscriptionReq = new NotificationSubscriptionRequest();
        subscriptionReq.setEmail("subscriber@example.com");
        subscriptionReq.setFlightNumber("FL123");
        subscriptionReq.setSubscribeToDelay(true);
        subscriptionReq.setSubscribeToArrival(false);
        subscriptionReq.setSubscribeToDeparture(true);

        Mockito.when(notificationSubscriptionRepository.save(any(NotificationSubscription.class))).thenThrow(new RuntimeException("Unable to save"));

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> notificationSubscriptionService.saveSubscription(subscriptionReq)
        );

        assertEquals("Unable to subscribe", exception.getMessage());
        Mockito.verify(notificationSubscriptionRepository).save(any(NotificationSubscription.class));
    }

    @Test
    void testGetSubscriberEmailsForFlightStatus_Success() {
        String flightNumber = "FL123";
        String status = "DELAYED";

        List<String> subscribers = new ArrayList<>();
        subscribers.add("subscriber1@example.com");
        subscribers.add("subscriber2@example.com");

        Mockito.when(notificationSubscriptionRepository.findSubscriberEmailsByFlightNumberAndStatus(flightNumber, status))
                .thenReturn(subscribers);

        List<String> result = notificationSubscriptionService.getSubscriberEmailsForFlightStatus(flightNumber, status);

        assertEquals(subscribers, result);
        Mockito.verify(notificationSubscriptionRepository).findSubscriberEmailsByFlightNumberAndStatus(flightNumber, status);
    }

    @Test
    void testGetSubscriberEmailsForFlightStatus_Exception() {
        String flightNumber = "FL123";
        String status = "DELAYED";

        Mockito.when(notificationSubscriptionRepository.findSubscriberEmailsByFlightNumberAndStatus(flightNumber, status))
                .thenThrow(new RuntimeException("Error fetching emails"));

        List<String> result = notificationSubscriptionService.getSubscriberEmailsForFlightStatus(flightNumber, status);

        assertEquals(0, result.size());
        Mockito.verify(notificationSubscriptionRepository).findSubscriberEmailsByFlightNumberAndStatus(flightNumber, status);
    }
}
