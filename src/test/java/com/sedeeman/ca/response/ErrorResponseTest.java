package com.sedeeman.ca.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@DisplayName("ErrorResponse Test")
class ErrorResponseTest {

    @Test
    @DisplayName("Test getters and setters")
    void testGettersAndSetters() {

        Integer code = 500;
        String status = "Internal Server Error";
        String message = "An error occurred";
        List<String> errors = Arrays.asList("Error 1", "Error 2");

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        errorResponse.setErrors(errors);

        assertEquals(code, errorResponse.getCode());
        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(errors, errorResponse.getErrors());
    }

    @Test
    @DisplayName("Test no-args constructor")
    void testNoArgsConstructor() {

        ErrorResponse errorResponse = new ErrorResponse();

        assertNull(errorResponse.getCode());
        assertNull(errorResponse.getStatus());
        assertNull(errorResponse.getMessage());
        assertNull(errorResponse.getErrors());
    }

    @Test
    @DisplayName("Test all-args constructor")
    void testAllArgsConstructor() {

        Integer code = 400;
        String status = "Bad Request";
        String message = "Validation error";
        List<String> errors = Arrays.asList("Error 1", "Error 2");

        ErrorResponse errorResponse = new ErrorResponse(code, status, message, errors);

        assertEquals(code, errorResponse.getCode());
        assertEquals(status, errorResponse.getStatus());
        assertEquals(message, errorResponse.getMessage());
        assertEquals(errors, errorResponse.getErrors());
    }

    @Test
    @DisplayName("Test JsonPropertyOrder annotation")
    void testJsonPropertyOrder() throws Exception {
        ErrorResponse errorResponse = new ErrorResponse(404, "Not Found", "Resource not found", Arrays.asList("Error 1", "Error 2"));

        String jsonString = new ObjectMapper().writeValueAsString(errorResponse);

        assertEquals("{\"code\":404,\"status\":\"Not Found\",\"message\":\"Resource not found\",\"errors\":[\"Error 1\",\"Error 2\"]}", jsonString);
    }
}