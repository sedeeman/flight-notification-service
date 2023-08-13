package com.sedeeman.ca.dto;

import com.sedeeman.ca.model.FlightStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("FlightUpdateRequest Tests")
class FlightUpdateRequestTest {

    @Test
    @DisplayName("Test Getters and Setters")
    void testGettersAndSetters() {
        FlightUpdateRequest request = new FlightUpdateRequest();
        request.setFlightNumber("Boeing737");
        request.setStatus(FlightStatus.DELAY);

        assertEquals("Boeing737", request.getFlightNumber());
        assertEquals(FlightStatus.DELAY, request.getStatus());
    }

    @Test
    @DisplayName("Test Equals and HashCode")
    void testEqualsAndHashCode() {
        FlightUpdateRequest request1 = new FlightUpdateRequest("Boeing737", FlightStatus.DELAY);
        FlightUpdateRequest request2 = new FlightUpdateRequest("Boeing737", FlightStatus.DELAY);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    @DisplayName("Test Empty Constructor")
    void testEmptyConstructor() {
        FlightUpdateRequest request = new FlightUpdateRequest();

        assertNull(request.getFlightNumber());
        assertNull(request.getStatus());
    }
}