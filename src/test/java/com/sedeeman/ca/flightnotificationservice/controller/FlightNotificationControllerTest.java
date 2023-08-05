package com.sedeeman.ca.flightnotificationservice.controller;

import com.sedeeman.ca.flightnotificationservice.dto.FlightStatusEvent;
import com.sedeeman.ca.flightnotificationservice.model.NotificationSubscription;
import com.sedeeman.ca.flightnotificationservice.dto.NotificationSubscriptionRequest;
import com.sedeeman.ca.flightnotificationservice.response.SuccessResponse;
import com.sedeeman.ca.flightnotificationservice.service.MessageSendingService;
import com.sedeeman.ca.flightnotificationservice.service.NotificationSubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FlightNotificationControllerTest {

    @Mock
    private NotificationSubscriptionService subscriptionService;

    @Mock
    private MessageSendingService messageSendingService;

    @InjectMocks
    private FlightNotificationController flightNotificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSubscribeForNotifications() {

        NotificationSubscriptionRequest subscriptionRequest = new NotificationSubscriptionRequest();
        subscriptionRequest.setEmail("test@example.com");
        subscriptionRequest.setSubscribeToDeparture(true);
        subscriptionRequest.setSubscribeToArrival(true);

        NotificationSubscription savedSubscription = new NotificationSubscription();
        savedSubscription.setId(1L);
        savedSubscription.setEmail(subscriptionRequest.getEmail());
        savedSubscription.setSubscribeToDeparture(subscriptionRequest.isSubscribeToDeparture());
        savedSubscription.setSubscribeToArrival(subscriptionRequest.isSubscribeToArrival());

        when(subscriptionService.saveSubscription(subscriptionRequest)).thenReturn(savedSubscription);

        ResponseEntity<SuccessResponse<NotificationSubscription>> responseEntity = flightNotificationController.subscribeForNotifications(subscriptionRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getCode());
        assertEquals("Successfully subscribed to email notifications", responseEntity.getBody().getMessage());
        assertEquals(savedSubscription, responseEntity.getBody().getData());
    }

    @Test
    void testUpdateFlightStatus() {
        FlightStatusEvent flightStatusEvent = new FlightStatusEvent();

        ResponseEntity<SuccessResponse<String>> responseEntity = flightNotificationController.updateFlightStatus(flightStatusEvent);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(HttpStatus.OK.value(), responseEntity.getBody().getCode());
        assertEquals("Successfully published flight status update", responseEntity.getBody().getMessage());
        assertEquals("", responseEntity.getBody().getData());

        verify(messageSendingService, times(1)).onFlightStatusUpdate(flightStatusEvent);
    }
}
