package com.sedeeman.ca.flightnotificationservice.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FlightStatusEventTest {

    @Test
    void testNoArgsConstructor() {

        FlightStatusEvent event = new FlightStatusEvent();

        assertNull(event.getFlightNumber());
        assertNull(event.getStatus());
    }

    @Test
    void testAllArgsConstructor() {

        String flightNumber = "FL123";
        String status = "Delayed";

        FlightStatusEvent event = new FlightStatusEvent(flightNumber, status);

        assertEquals(flightNumber, event.getFlightNumber());
        assertEquals(status, event.getStatus());
    }

    @Test
    void testSetterGetterMethods() {

        FlightStatusEvent event = new FlightStatusEvent();

        event.setFlightNumber("FL456");
        event.setStatus("Arrived");

        assertEquals("FL456", event.getFlightNumber());
        assertEquals("Arrived", event.getStatus());
    }

    @Test
    void testEqualsAndHashCode() {

        FlightStatusEvent event1 = new FlightStatusEvent("FL123", "On Time");
        FlightStatusEvent event2 = new FlightStatusEvent("FL123", "On Time");
        FlightStatusEvent event3 = new FlightStatusEvent("FL456", "Delayed");

        assertEquals(event1, event2);
        assertNotEquals(event1, event3);
        assertEquals(event1.hashCode(), event2.hashCode());
        assertNotEquals(event1.hashCode(), event3.hashCode());
    }
}
