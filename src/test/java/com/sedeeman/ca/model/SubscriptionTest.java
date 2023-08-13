package com.sedeeman.ca.model;

import com.sedeeman.ca.model.Subscription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Subscription Tests")
class SubscriptionTest {

    @Test
    @DisplayName("Test no-args constructor")
    void testNoArgsConstructor() {
        Subscription subscription = new Subscription();

        assertNull(subscription.getId());
        assertNull(subscription.getEmail());
        assertNull(subscription.getFlightNumber());
        assertFalse(subscription.isSubscribeToDelay());
        assertFalse(subscription.isSubscribeToArrival());
        assertFalse(subscription.isSubscribeToDeparture());
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {

        Long id = 1L;
        String email = "subscriber@example.com";
        String flightNumber = "FL123";
        boolean subscribeToDelay = true;
        boolean subscribeToArrival = false;
        boolean subscribeToDeparture = true;

        Subscription subscription = new Subscription(id, email, flightNumber, subscribeToDelay, subscribeToArrival, subscribeToDeparture);

        assertEquals(id, subscription.getId());
        assertEquals(email, subscription.getEmail());
        assertEquals(flightNumber, subscription.getFlightNumber());
        assertTrue(subscription.isSubscribeToDelay());
        assertFalse(subscription.isSubscribeToArrival());
        assertTrue(subscription.isSubscribeToDeparture());
    }

    @Test
    @DisplayName("Test setter and getter methods")
    void testSetterGetterMethods() {

        Subscription subscription = new Subscription();

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
