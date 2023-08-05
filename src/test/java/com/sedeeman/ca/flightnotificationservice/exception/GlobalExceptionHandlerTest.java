package com.sedeeman.ca.flightnotificationservice.exception;

import com.sedeeman.ca.flightnotificationservice.exception.GlobalExceptionHandler;
import com.sedeeman.ca.flightnotificationservice.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleValidationException() {
        String field1 = "name";
        String message1 = "Name is required";
        String field2 = "age";
        String message2 = "Age must be greater than 0";

        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("objectName", field1, message1));
        fieldErrors.add(new FieldError("objectName", field2, message2));

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException((MethodParameter) null, bindingResult);

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleValidationException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getCode());
        List<String> errors = errorResponse.getErrors();
        assertEquals(2, errors.size());
        assertEquals(field1 + " " + message1, errors.get(0));
        assertEquals(field2 + " " + message2, errors.get(1));
    }

    @Test
    void testHandleGenericException() {

        String errorMessage = "Something went wrong";
        Exception exception = new Exception(errorMessage);

        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleGenericException(exception);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponse.getCode());
        assertEquals("An error occurred", errorResponse.getMessage());
        assertEquals(1, errorResponse.getErrors().size());
        assertEquals(errorMessage, errorResponse.getErrors().get(0));
    }
}

