package com.sedeeman.ca.repository;


import com.sedeeman.ca.model.Subscription;
import com.sedeeman.ca.util.Constant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightNotificationRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT DISTINCT s.email FROM Subscription s " +
            "WHERE s.flightNumber = :flightNumber AND " +
            "((:status = '" + Constant.DELAY + "' AND s.subscribeToDelay = true) OR " +
            "(:status = '" + Constant.ARRIVAL + "' AND s.subscribeToArrival = true) OR " +
            "(:status = '" + Constant.DEPARTURE + "' AND s.subscribeToDeparture = true))")
    List<String> findSubscribers(
            @Param("flightNumber") String flightNumber,
            @Param("status") String status);



    @Query("SELECT s FROM Subscription s WHERE s.flightNumber = :flightNumber")
    Optional<Subscription> findByFlightNumber(@Param("flightNumber") String flightNumber);

}
