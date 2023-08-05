package com.sedeeman.ca.flightnotificationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NotificationSubscriptionRequest {

    @NonNull
    @NotBlank
    private String email;

    @NonNull
    @NotBlank
    private String flightNumber;

    private boolean subscribeToDelay;

    private boolean subscribeToArrival;

    private boolean subscribeToDeparture;
}
