package com.sedeeman.ca.flightnotificationservice.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationSubscriptionRequestTest {

    @Test
    void testNoArgsConstructor() {
        NotificationSubscriptionRequest request = new NotificationSubscriptionRequest();

        assertNull(request.getEmail());
        assertNull(request.getFlightNumber());
        assertFalse(request.isSubscribeToDelay());
        assertFalse(request.isSubscribeToArrival());
        assertFalse(request.isSubscribeToDeparture());
    }

    @Test
    void testAllArgsConstructor() {
        String email = "subscriber@example.com";
        String flightNumber = "FL123";
        boolean subscribeToDelay = true;
        boolean subscribeToArrival = false;
        boolean subscribeToDeparture = true;

        NotificationSubscriptionRequest request = new NotificationSubscriptionRequest(email, flightNumber, subscribeToDelay, subscribeToArrival, subscribeToDeparture);

        assertEquals(email, request.getEmail());
        assertEquals(flightNumber, request.getFlightNumber());
        assertTrue(request.isSubscribeToDelay());
        assertFalse(request.isSubscribeToArrival());
        assertTrue(request.isSubscribeToDeparture());
    }

    @Test
    void testSetterGetterMethods() {
        NotificationSubscriptionRequest request = new NotificationSubscriptionRequest();

        request.setEmail("new-subscriber@example.com");
        request.setFlightNumber("FL456");
        request.setSubscribeToDelay(false);
        request.setSubscribeToArrival(true);
        request.setSubscribeToDeparture(false);

        assertEquals("new-subscriber@example.com", request.getEmail());
        assertEquals("FL456", request.getFlightNumber());
        assertFalse(request.isSubscribeToDelay());
        assertTrue(request.isSubscribeToArrival());
        assertFalse(request.isSubscribeToDeparture());
    }

    @Test
    void testEqualsAndHashCode() {
        NotificationSubscriptionRequest request1 = new NotificationSubscriptionRequest("a@example.com", "FL123", true, false, true);
        NotificationSubscriptionRequest request2 = new NotificationSubscriptionRequest("a@example.com", "FL123", true, false, true);
        NotificationSubscriptionRequest request3 = new NotificationSubscriptionRequest("b@example.com", "FL456", false, true, false);

        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
        assertNotEquals(request1.hashCode(), request3.hashCode());
    }
}
