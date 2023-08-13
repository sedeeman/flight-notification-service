package com.sedeeman.ca.service;

import com.sedeeman.ca.dto.SubscriptionRequest;
import com.sedeeman.ca.dto.FlightUpdateRequest;
import com.sedeeman.ca.exception.SubscriptionException;
import com.sedeeman.ca.model.Subscription;
import com.sedeeman.ca.repository.FlightNotificationRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FlightNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(FlightNotificationService.class);

    private FlightNotificationRepository flightNotificationRepository;

    private final EmailService emailService;


    public Subscription addSubscribers(SubscriptionRequest subscriptionReq) {
        Optional<Subscription> optionalSubscription = flightNotificationRepository.findByFlightNumber(subscriptionReq.getFlightNumber());

        Subscription subscription;

        try {
            if (optionalSubscription.isEmpty()) {
                subscription = new Subscription();
                subscription.setEmail(subscriptionReq.getEmail());
                subscription.setFlightNumber(subscriptionReq.getFlightNumber());
            } else {
                subscription = optionalSubscription.get();
            }

            subscription.setSubscribeToDelay(subscriptionReq.isSubscribeToDelay());
            subscription.setSubscribeToArrival(subscriptionReq.isSubscribeToArrival());
            subscription.setSubscribeToDeparture(subscriptionReq.isSubscribeToDeparture());

            String action = optionalSubscription.isEmpty() ? "added" : "updated";
            logger.info("Successfully {} the subscription", action);

            return flightNotificationRepository.save(subscription);

        } catch (Exception e) {
            String action = optionalSubscription.isEmpty() ? "add" : "update";
            throw new SubscriptionException(String.format("Unable to %s subscription", action), e);
        }
    }


    public List<String> getSubscribers(String flightNumber, String status) {
        try {
            return flightNotificationRepository.findSubscribers(flightNumber, status);

        } catch (Exception e) {

            logger.error(String.format("Error while fetching subscribers emails: %s", e.getMessage()));
            return Collections.emptyList();
        }
    }


    @RabbitListener(queues = "email-queue")
    public void consumeFlightStatusUpdate(FlightUpdateRequest flightUpdateRequest) {

        List<String> subscriberEmails = getSubscribers(flightUpdateRequest.getFlightNumber(), String.valueOf(flightUpdateRequest.getStatus()));

        try {
            for (String userEmail : subscriberEmails) {
                emailService.sendEmail(userEmail, "Flight Status Update", String.format("Flight %s status: %s", flightUpdateRequest.getFlightNumber(), flightUpdateRequest.getStatus()));
                logger.info("Email sent successfully to: {}", userEmail);
            }

        } catch (MailSendException e) {
            throw new AmqpRejectAndDontRequeueException("Failed to process email notification", e);
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException("Unexpected error occurred while processing email notification", e);
        }

    }


}
