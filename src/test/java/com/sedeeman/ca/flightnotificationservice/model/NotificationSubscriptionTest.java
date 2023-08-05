package com.sedeeman.ca.flightnotificationservice.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NotificationSubscriptionTest {

    @Test
    void testNoArgsConstructor() {
        NotificationSubscription subscription = new NotificationSubscription();

        assertNull(subscription.getId());
        assertNull(subscription.getEmail());
        assertNull(subscription.getFlightNumber());
        assertFalse(subscription.isSubscribeToDelay());
        assertFalse(subscription.isSubscribeToArrival());
        assertFalse(subscription.isSubscribeToDeparture());
    }

    @Test
    void testAllArgsConstructor() {

        Long id = 1L;
        String email = "subscriber@example.com";
        String flightNumber = "FL123";
        boolean subscribeToDelay = true;
        boolean subscribeToArrival = false;
        boolean subscribeToDeparture = true;

        NotificationSubscription subscription = new NotificationSubscription(id, email, flightNumber, subscribeToDelay, subscribeToArrival, subscribeToDeparture);

        assertEquals(id, subscription.getId());
        assertEquals(email, subscription.getEmail());
        assertEquals(flightNumber, subscription.getFlightNumber());
        assertTrue(subscription.isSubscribeToDelay());
        assertFalse(subscription.isSubscribeToArrival());
        assertTrue(subscription.isSubscribeToDeparture());
    }

    @Test
    void testSetterGetterMethods() {

        NotificationSubscription subscription = new NotificationSubscription();

        subscription.setId(2L);
        subscription.setEmail("subscriber2@example.com");
        subscription.setFlightNumber("FL456");
        subscription.setSubscribeToDelay(false);
        subscription.setSubscribeToArrival(true);
        subscription.setSubscribeToDeparture(false);

        assertEquals(2L, subscription.getId());
        assertEquals("subscriber2@example.com", subscription.getEmail());
        assertEquals("FL456", subscription.getFlightNumber());
        assertFalse(subscription.isSubscribeToDelay());
        assertTrue(subscription.isSubscribeToArrival());
        assertFalse(subscription.isSubscribeToDeparture());
    }
}
