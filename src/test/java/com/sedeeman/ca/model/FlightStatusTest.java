package com.sedeeman.ca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightStatusTest {

    @Test
    @DisplayName("Should give String value of FlightStatus enums")
    void ShouldGiveFlightStatusStringValues() {

        assertEquals(4, FlightStatus.values().length);

        assertEquals(FlightStatus.ARRIVAL, FlightStatus.valueOf("ARRIVAL"));
        assertEquals(FlightStatus.DEPARTURE, FlightStatus.valueOf("DEPARTURE"));
        assertEquals(FlightStatus.DELAY, FlightStatus.valueOf("DELAY"));
        assertEquals(FlightStatus.SCHEDULED, FlightStatus.valueOf("SCHEDULED"));
    }
}
