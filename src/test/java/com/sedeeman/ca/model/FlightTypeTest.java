package com.sedeeman.ca.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FlightTypeTest {

    @Test
    @DisplayName("Should give String value of FlightType enums")
    void ShouldGiveFlightTypeStringValues() {

        assertEquals(2, FlightType.values().length);

        assertEquals(FlightType.INBOUND, FlightType.valueOf("INBOUND"));
        assertEquals(FlightType.OUTBOUND, FlightType.valueOf("OUTBOUND"));
    }
}