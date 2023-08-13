package com.sedeeman.ca.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Subscription Request Tests")
class SubscriptionRequestTest {

    @Test
    @DisplayName("Test subscription request creation")
    void testSubscriptionRequestCreation() {
        String email = "subscriber@example.com";
        String flightNumber = "ABC123";
        boolean subscribeToDelay = true;
        boolean subscribeToArrival = false;
        boolean subscribeToDeparture = true;

        SubscriptionRequest subscriptionRequest = new SubscriptionRequest(email, flightNumber, subscribeToDelay, subscribeToArrival, subscribeToDeparture);

        assertNotNull(subscriptionRequest);
        assertEquals(email, subscriptionRequest.getEmail());
        assertEquals(flightNumber, subscriptionRequest.getFlightNumber());
        assertEquals(subscribeToDelay, subscriptionRequest.isSubscribeToDelay());
        assertEquals(subscribeToArrival, subscriptionRequest.isSubscribeToArrival());
        assertEquals(subscribeToDeparture, subscriptionRequest.isSubscribeToDeparture());
    }
}
