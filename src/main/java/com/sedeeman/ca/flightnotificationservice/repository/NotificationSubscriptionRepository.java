package com.sedeeman.ca.flightnotificationservice.repository;


import com.sedeeman.ca.flightnotificationservice.model.NotificationSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationSubscriptionRepository extends JpaRepository<NotificationSubscription, Long> {

    @Query("SELECT ns.email FROM NotificationSubscription ns " +
            "WHERE ns.flightNumber = :flightNumber AND " +
            "(:status = 'delay' AND ns.subscribeToDelay = true) OR " +
            "(:status = 'departure' AND ns.subscribeToDeparture = true) OR " +
            "(:status = 'arrival' AND ns.subscribeToArrival = true)")
    List<String> findSubscriberEmailsByFlightNumberAndStatus(String flightNumber, String status);

}
