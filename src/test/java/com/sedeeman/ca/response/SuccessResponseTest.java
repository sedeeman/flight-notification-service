package com.sedeeman.ca.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("SuccessResponse Test")
class SuccessResponseTest {

    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {
        Integer code = 200;
        String status = "OK";
        String message = "Success";
        List<String> data = Arrays.asList("Data 1", "Data 2");

        SuccessResponse<List<String>> successResponse = new SuccessResponse<>();
        successResponse.setCode(code);
        successResponse.setStatus(status);
        successResponse.setMessage(message);
        successResponse.setData(data);

        assertEquals(code, successResponse.getCode());
        assertEquals(status, successResponse.getStatus());
        assertEquals(message, successResponse.getMessage());
        assertEquals(data, successResponse.getData());
    }

    @Test
    @DisplayName("Test no-args constructor")
    void testNoArgsConstructor() {
        SuccessResponse<String> successResponse = new SuccessResponse<>();

        assertNull(successResponse.getCode());
        assertNull(successResponse.getStatus());
        assertNull(successResponse.getMessage());
        assertNull(successResponse.getData());
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {
        Integer code = 200;
        String status = "OK";
        String message = "Success";
        String data = "Data";

        SuccessResponse<String> successResponse = new SuccessResponse<>(code, status, message, data);

        assertEquals(code, successResponse.getCode());
        assertEquals(status, successResponse.getStatus());
        assertEquals(message, successResponse.getMessage());
        assertEquals(data, successResponse.getData());
    }

    @Test
    @DisplayName("Test JsonPropertyOrder annotation")
    void testJsonPropertyOrder() throws Exception {
        SuccessResponse<String> successResponse = new SuccessResponse<>(200, "OK", "Success", "Data");

        String jsonString = new ObjectMapper().writeValueAsString(successResponse);

        assertEquals("{\"code\":200,\"status\":\"OK\",\"message\":\"Success\",\"data\":\"Data\"}", jsonString);
    }
}