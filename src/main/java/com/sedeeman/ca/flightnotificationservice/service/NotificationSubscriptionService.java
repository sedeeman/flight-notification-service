package com.sedeeman.ca.flightnotificationservice.service;

import com.sedeeman.ca.flightnotificationservice.exception.SubscriptionException;
import com.sedeeman.ca.flightnotificationservice.model.NotificationSubscription;
import com.sedeeman.ca.flightnotificationservice.repository.NotificationSubscriptionRepository;
import com.sedeeman.ca.flightnotificationservice.dto.NotificationSubscriptionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NotificationSubscriptionService {

    @Autowired
    private NotificationSubscriptionRepository notificationSubscriptionRepository;

    private static final Logger logger = LoggerFactory.getLogger(NotificationSubscriptionService.class);

    public NotificationSubscription saveSubscription(NotificationSubscriptionRequest subscriptionReq) {

        try {
            NotificationSubscription subscription = new NotificationSubscription();
            subscription.setEmail(subscriptionReq.getEmail());
            subscription.setFlightNumber(subscriptionReq.getFlightNumber());
            subscription.setSubscribeToDelay(subscriptionReq.isSubscribeToDelay());
            subscription.setSubscribeToArrival(subscriptionReq.isSubscribeToArrival());
            subscription.setSubscribeToDeparture(subscriptionReq.isSubscribeToDeparture());

            logger.info("Successfully added the flight");

            return notificationSubscriptionRepository.save(subscription);

        } catch (Exception e) {
            throw new SubscriptionException("Unable to subscribe", e);
        }
    }


    public List<String> getSubscriberEmailsForFlightStatus(String flightNumber, String status) {
        try {
            return notificationSubscriptionRepository.findSubscriberEmailsByFlightNumberAndStatus(flightNumber, status);

        } catch (Exception e) {

            logger.error(String.format("Error while fetching subscriber emails for flight status : %s", e.getMessage()));
            return Collections.emptyList();
        }
    }


}
