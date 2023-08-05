package com.sedeeman.ca.flightnotificationservice.repository;

import com.sedeeman.ca.flightnotificationservice.model.NotificationSubscription;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig
@DataJpaTest
class NotificationSubscriptionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NotificationSubscriptionRepository notificationSubscriptionRepository;

    @Test
    void testFindSubscriberEmailsByFlightNumberAndStatus() {
        NotificationSubscription subscription1 = new NotificationSubscription(null, "subscriber1@example.com", "FL123", true, false, true);
        NotificationSubscription subscription2 = new NotificationSubscription(null, "subscriber2@example.com", "FL123", false, true, true);
        NotificationSubscription subscription3 = new NotificationSubscription(null, "subscriber3@example.com", "FL456", true, true, false);

        entityManager.persist(subscription1);
        entityManager.persist(subscription2);
        entityManager.persist(subscription3);
        entityManager.flush();

        List<String> subscribersFL123Delay = notificationSubscriptionRepository.findSubscriberEmailsByFlightNumberAndStatus("FL123", "delay");
        List<String> subscribersFL123Departure = notificationSubscriptionRepository.findSubscriberEmailsByFlightNumberAndStatus("FL123", "departure");
        List<String> subscribersFL123Arrival = notificationSubscriptionRepository.findSubscriberEmailsByFlightNumberAndStatus("FL123", "arrival");
        List<String> subscribersFL456Delay = notificationSubscriptionRepository.findSubscriberEmailsByFlightNumberAndStatus("FL456", "delay");

        assertEquals(Arrays.asList("subscriber1@example.com"), subscribersFL123Delay);
        assertEquals(Arrays.asList("subscriber1@example.com", "subscriber2@example.com"), subscribersFL123Departure);
        assertEquals(Arrays.asList("subscriber2@example.com", "subscriber3@example.com"), subscribersFL123Arrival);
        assertEquals(Arrays.asList("subscriber3@example.com"), subscribersFL456Delay);
    }
}



