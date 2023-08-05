package com.sedeeman.ca.flightnotificationservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification_subscription")
public class NotificationSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String flightNumber;

    private boolean subscribeToDelay;

    private boolean subscribeToArrival;

    private boolean subscribeToDeparture;

}
