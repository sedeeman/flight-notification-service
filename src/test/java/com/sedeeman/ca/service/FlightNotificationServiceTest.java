package com.sedeeman.ca.service;

import com.sedeeman.ca.dto.FlightUpdateRequest;
import com.sedeeman.ca.dto.SubscriptionRequest;
import com.sedeeman.ca.exception.SubscriptionException;
import com.sedeeman.ca.model.FlightStatus;
import com.sedeeman.ca.model.Subscription;
import com.sedeeman.ca.repository.FlightNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Flight Notification Service Tests")
class FlightNotificationServiceTest {

    @Mock
    private FlightNotificationRepository flightNotificationRepository;

    @Mock
    private EmailService emailService;


    @InjectMocks
    private FlightNotificationService flightNotificationService;

    @Test
    @DisplayName("Test addSubscribers - Add")
    void testAddSubscribers_Add() {
        SubscriptionRequest subscriptionReq = new SubscriptionRequest("subscriber1@example.com", "FL123", true, false, true);
        Subscription subscription = new Subscription(1L, "subscriber1@example.com", "FL123", true, false, true);

        when(flightNotificationRepository.findByFlightNumber(subscriptionReq.getFlightNumber())).thenReturn(Optional.empty());
        when(flightNotificationRepository.save(any(Subscription.class))).thenReturn(subscription);

        Subscription result = flightNotificationService.addSubscribers(subscriptionReq);

        assertEquals(subscription, result);

        verify(flightNotificationRepository, times(1)).findByFlightNumber(subscriptionReq.getFlightNumber());
        verify(flightNotificationRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    @DisplayName("Test addSubscribers - Update")
    void testAddSubscribers_Update() {
        SubscriptionRequest subscriptionReq = new SubscriptionRequest("subscriber1@example.com", "FL123", true, false, true);
        Subscription existingSubscription = new Subscription(1L, "subscriber1@example.com", "FL123", true, false, false);
        Subscription updatedSubscription = new Subscription(1L, "subscriber1@example.com", "FL123", true, false, true);

        when(flightNotificationRepository.findByFlightNumber(subscriptionReq.getFlightNumber())).thenReturn(Optional.of(existingSubscription));
        when(flightNotificationRepository.save(any(Subscription.class))).thenReturn(updatedSubscription);

        Subscription result = flightNotificationService.addSubscribers(subscriptionReq);

        assertEquals(updatedSubscription, result);

        verify(flightNotificationRepository, times(1)).findByFlightNumber(subscriptionReq.getFlightNumber());
        verify(flightNotificationRepository, times(1)).save(any(Subscription.class));
    }

    @Test
    @DisplayName("Test addSubscribers - Error")
    void testAddSubscribers_Error() {
        SubscriptionRequest subscriptionReq = new SubscriptionRequest("subscriber1@example.com", "FL123", true, false, true);

        when(flightNotificationRepository.findByFlightNumber(subscriptionReq.getFlightNumber())).thenReturn(Optional.empty());
        when(flightNotificationRepository.save(any(Subscription.class))).thenThrow(new RuntimeException());

        assertThrows(SubscriptionException.class, () -> flightNotificationService.addSubscribers(subscriptionReq));

        verify(flightNotificationRepository, times(1)).findByFlightNumber(subscriptionReq.getFlightNumber());
        verify(flightNotificationRepository, times(1)).save(any(Subscription.class));
    }


    @Test
    @DisplayName("Test addSubscribers - Error during Update")
    void testAddSubscribers_ErrorDuringUpdate() {
        SubscriptionRequest subscriptionReq = new SubscriptionRequest("subscriber1@example.com", "FL123", true, false, true);
        Subscription existingSubscription = new Subscription(1L, "subscriber1@example.com", "FL123", true, false, false);

        when(flightNotificationRepository.findByFlightNumber(subscriptionReq.getFlightNumber())).thenReturn(Optional.of(existingSubscription));
        when(flightNotificationRepository.save(any(Subscription.class))).thenThrow(new RuntimeException());

        assertThrows(SubscriptionException.class, () -> flightNotificationService.addSubscribers(subscriptionReq));

        verify(flightNotificationRepository, times(1)).findByFlightNumber(subscriptionReq.getFlightNumber());
        verify(flightNotificationRepository, times(1)).save(any(Subscription.class));
    }


    @Test
    @DisplayName("Get Subscribers Successfully")
    void testGetSubscribers_Success() {
        String flightNumber = "FL123";
        String status = "DELAYED";

        List<String> subscribers = new ArrayList<>();
        subscribers.add("subscriber1@example.com");
        subscribers.add("subscriber2@example.com");

        when(flightNotificationRepository.findSubscribers(flightNumber, status))
                .thenReturn(subscribers);

        List<String> result = flightNotificationService.getSubscribers(flightNumber, status);

        assertEquals(subscribers, result);
        verify(flightNotificationRepository).findSubscribers(flightNumber, status);
    }

    @Test
    @DisplayName("Get Subscribers - Error")
    void testGetSubscribers_Exception() {
        String flightNumber = "FL123";
        String status = "DELAYED";

        when(flightNotificationRepository.findSubscribers(flightNumber, status))
                .thenThrow(new RuntimeException("Error while fetching subscribers emails:"));

        List<String> result = flightNotificationService.getSubscribers(flightNumber, status);

        assertEquals(0, result.size());
        verify(flightNotificationRepository).findSubscribers(flightNumber, status);
    }


    @Test
    @DisplayName("Consume Flight Status Update - Successful Email Sending")
    void testConsumeFlightStatusUpdate_SuccessfulEmailSending() {
        FlightUpdateRequest request = createTestFlightUpdateRequest();
        List<String> subscribers = Arrays.asList("email1", "email2");

        when(flightNotificationRepository.findSubscribers(request.getFlightNumber(), String.valueOf(request.getStatus())))
                .thenReturn(subscribers);
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        assertDoesNotThrow(() -> flightNotificationService.consumeFlightStatusUpdate(request));

        verify(flightNotificationRepository, times(1))
                .findSubscribers(request.getFlightNumber(), String.valueOf(request.getStatus()));

        verify(emailService, times(subscribers.size())).sendEmail(anyString(), anyString(), anyString());
    }


    @Test
    @DisplayName("Consume Flight Status Update - MailException")
    void testConsumeFlightStatusUpdate_MailException() {
        FlightUpdateRequest request = createTestFlightUpdateRequest();

        when(flightNotificationRepository.findSubscribers(request.getFlightNumber(), String.valueOf(request.getStatus())))
                .thenReturn(Arrays.asList("email1", "email2"));

        doThrow(new MailSendException("Email sending failed"))
                .when(emailService).sendEmail(anyString(), anyString(), anyString());

        assertThrows(AmqpRejectAndDontRequeueException.class, () -> {
            flightNotificationService.consumeFlightStatusUpdate(request);
        });

        verify(flightNotificationRepository, times(1))
                .findSubscribers(request.getFlightNumber(), String.valueOf(request.getStatus()));

        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }


    @Test
    @DisplayName("Consume Flight Status Update - Unexpected Error")
    void testConsumeFlightStatusUpdate_UnexpectedError() {
        FlightUpdateRequest request = createTestFlightUpdateRequest();

        when(flightNotificationRepository.findSubscribers(request.getFlightNumber(), String.valueOf(request.getStatus())))
                .thenReturn(Arrays.asList("email1", "email2"));

        doThrow(RuntimeException.class).when(emailService).sendEmail(anyString(), anyString(), anyString());

        assertThrows(AmqpRejectAndDontRequeueException.class, () -> {
            flightNotificationService.consumeFlightStatusUpdate(request);
        });

        verify(flightNotificationRepository, times(1))
                .findSubscribers(request.getFlightNumber(), String.valueOf(request.getStatus()));

        verify(emailService, times(1)).sendEmail(anyString(), anyString(), anyString());
    }


    private SubscriptionRequest createTestSubscriptionRequest() {
        SubscriptionRequest subscriptionReq = new SubscriptionRequest();
        subscriptionReq.setEmail("subscriber@example.com");
        subscriptionReq.setFlightNumber("FL123");
        subscriptionReq.setSubscribeToDelay(true);
        subscriptionReq.setSubscribeToArrival(false);
        subscriptionReq.setSubscribeToDeparture(true);
        return subscriptionReq;
    }

    private Subscription createTestSubscription() {
        Subscription subscription = new Subscription();
        subscription.setEmail("subscriber@example.com");
        subscription.setFlightNumber("FL123");
        subscription.setSubscribeToDelay(true);
        subscription.setSubscribeToArrival(false);
        subscription.setSubscribeToDeparture(true);

        return subscription;
    }

    private FlightUpdateRequest createTestFlightUpdateRequest() {
        FlightUpdateRequest updateRequest = new FlightUpdateRequest();
        updateRequest.setFlightNumber("FL123");
        updateRequest.setStatus(FlightStatus.DELAY);
        return updateRequest;
    }

}
