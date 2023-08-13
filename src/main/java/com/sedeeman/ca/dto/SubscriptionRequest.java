package com.sedeeman.ca.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SubscriptionRequest {

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
