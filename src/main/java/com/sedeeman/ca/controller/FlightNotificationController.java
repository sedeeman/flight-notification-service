package com.sedeeman.ca.controller;

import com.sedeeman.ca.dto.SubscriptionRequest;
import com.sedeeman.ca.model.Subscription;
import com.sedeeman.ca.response.SuccessResponse;
import com.sedeeman.ca.service.FlightNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/flight-notification-service")
@AllArgsConstructor
public class FlightNotificationController {
    private FlightNotificationService flightNotificationService;


    /**
     * Add a Subscription.
     *
     * @param subscriptionReq
     * @return Flight
     */
    @PostMapping("/subscribe")
    @Operation(description = "Successfully Add a subscription", responses = {
            @ApiResponse(
                    responseCode = "201",
                    ref = "postSuccessAPI"
            ),
            @ApiResponse(
                    responseCode = "400",
                    ref = "badRequestAPI"
            ),
            @ApiResponse(
                    responseCode = "500",
                    ref = "internalServerErrorAPI"
            )

    })
    public ResponseEntity<SuccessResponse<Subscription>> subscribeForNotifications(@RequestBody @Valid SubscriptionRequest subscriptionReq) {
        Subscription subscription = flightNotificationService.addSubscribers(subscriptionReq);
        return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Successfully subscribed to email notifications", subscription), HttpStatus.OK);
    }
}
