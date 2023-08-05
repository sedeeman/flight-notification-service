package com.sedeeman.ca.flightnotificationservice.controller;

import com.sedeeman.ca.flightnotificationservice.service.MessageSendingService;
import com.sedeeman.ca.flightnotificationservice.dto.FlightStatusEvent;
import com.sedeeman.ca.flightnotificationservice.model.NotificationSubscription;
import com.sedeeman.ca.flightnotificationservice.dto.NotificationSubscriptionRequest;
import com.sedeeman.ca.flightnotificationservice.response.SuccessResponse;
import com.sedeeman.ca.flightnotificationservice.service.NotificationSubscriptionService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/flights")
@CrossOrigin
public class FlightNotificationController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private NotificationSubscriptionService notificationSubscriptionService;

    @Autowired
    private MessageSendingService messageSendingService;

    @PostMapping("/subscribe")
    public ResponseEntity<SuccessResponse<NotificationSubscription>> subscribeForNotifications(@RequestBody @Valid NotificationSubscriptionRequest subscriptionReq) {
        NotificationSubscription notificationSubscription = notificationSubscriptionService.saveSubscription(subscriptionReq);
        return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Successfully subscribed to email notifications", notificationSubscription), HttpStatus.OK);
    }

    @PostMapping("/flight-status")
    public ResponseEntity<SuccessResponse<String>> updateFlightStatus(@RequestBody  @Valid FlightStatusEvent event) {
        messageSendingService.onFlightStatusUpdate(event);
        return new ResponseEntity<>(new SuccessResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), "Successfully published flight status update", ""), HttpStatus.OK);
    }
}
