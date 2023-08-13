package com.sedeeman.ca.controller;

import com.sedeeman.ca.model.Subscription;
import com.sedeeman.ca.dto.SubscriptionRequest;
import com.sedeeman.ca.response.SuccessResponse;
import com.sedeeman.ca.service.FlightNotificationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Flight Notification Controller Tests")
@ExtendWith(MockitoExtension.class)
class FlightNotificationControllerTest {

    @Mock
    private FlightNotificationService flightNotificationService;

    @InjectMocks
    private FlightNotificationController flightNotificationController;



    @Test
    @DisplayName("Test add subscription request")
    void testSubscribeForNotifications() throws Exception {

        SubscriptionRequest request = new SubscriptionRequest();
        request.setEmail("subscriber@gmail.com");
        request.setFlightNumber("F737");
        request.setSubscribeToArrival(false);
        request.setSubscribeToArrival(false);
        request.setSubscribeToDelay(true);


        Subscription expectedResult = new Subscription();
        expectedResult.setEmail("subscriber@gmail.com");
        expectedResult.setFlightNumber("F737");
        expectedResult.setSubscribeToArrival(false);
        expectedResult.setSubscribeToArrival(false);
        expectedResult.setSubscribeToDelay(true);

        when(flightNotificationService.addSubscribers(any(SubscriptionRequest.class))).thenReturn(expectedResult);

        ResponseEntity<SuccessResponse<Subscription>> actualResult = flightNotificationController.subscribeForNotifications(request);

        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
        assertEquals(HttpStatus.OK.value(), actualResult.getBody().getCode());
        assertEquals(HttpStatus.OK.getReasonPhrase(), actualResult.getBody().getStatus());
        assertEquals("Successfully subscribed to email notifications", actualResult.getBody().getMessage());
        assertEquals(expectedResult.getEmail(), actualResult.getBody().getData().getEmail());
        assertEquals(expectedResult, actualResult.getBody().getData());

        verify(flightNotificationService).addSubscribers(request);
    }

}
