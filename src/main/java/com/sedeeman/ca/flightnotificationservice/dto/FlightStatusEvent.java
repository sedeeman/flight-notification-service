package com.sedeeman.ca.flightnotificationservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FlightStatusEvent implements Serializable {

    @NonNull
    @NotBlank
    private String flightNumber;

    @NonNull
    @NotBlank
    private String status;
}
