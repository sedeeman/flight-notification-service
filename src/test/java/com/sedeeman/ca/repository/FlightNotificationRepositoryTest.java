package com.sedeeman.ca.repository;

import com.sedeeman.ca.model.Subscription;
import com.sedeeman.ca.util.Constant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DisplayName("FlightNotificationRepository Custom Query Tests")
class FlightNotificationRepositoryCustomQueryTest {

    @Autowired
    private FlightNotificationRepository flightNotificationRepository;

    @Test
    @DisplayName("Test findSubscribers")
    void testFindSubscribers() {
        Subscription subscription1 = new Subscription(null, "subscriber1@example.com", "FL123", true, false, true);
        Subscription subscription2 = new Subscription(null, "subscriber2@example.com", "FL123", false, true, true);
        Subscription subscription3 = new Subscription(null, "subscriber3@example.com", "FL456", true, true, false);

        flightNotificationRepository.saveAll(List.of(subscription1, subscription2, subscription3));

        List<String> subscribersFL123Delay = flightNotificationRepository.findSubscribers("FL123", Constant.DELAY);
        List<String> subscribersFL123Departure = flightNotificationRepository.findSubscribers("FL123", Constant.DEPARTURE);
        List<String> subscribersFL123Arrival = flightNotificationRepository.findSubscribers("FL123", Constant.ARRIVAL);
        List<String> subscribersFL456Delay = flightNotificationRepository.findSubscribers("FL456", Constant.DELAY);

        assertEquals(List.of("subscriber1@example.com"), subscribersFL123Delay);
        assertEquals(List.of("subscriber1@example.com", "subscriber2@example.com"), subscribersFL123Departure);
        assertEquals(List.of("subscriber2@example.com"), subscribersFL123Arrival);
        assertEquals(List.of("subscriber3@example.com"), subscribersFL456Delay);
    }
}
