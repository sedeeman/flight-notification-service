package com.sedeeman.ca.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Constant Test")
class ConstantTest {

    @Test
    @DisplayName("Test constant value")
    void testConstantValue() {
        assertEquals("ARRIVAL", Constant.ARRIVAL);
        assertEquals("DEPARTURE", Constant.DEPARTURE);
        assertEquals("DELAY", Constant.DELAY);
    }

}