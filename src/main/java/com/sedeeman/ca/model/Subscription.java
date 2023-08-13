package com.sedeeman.ca.model;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String flightNumber;

    private boolean subscribeToDelay;

    private boolean subscribeToArrival;

    private boolean subscribeToDeparture;

}
