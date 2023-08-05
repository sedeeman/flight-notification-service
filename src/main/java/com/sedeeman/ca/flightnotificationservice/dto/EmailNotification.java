package com.sedeeman.ca.flightnotificationservice.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EmailNotification implements Serializable {
    private String to;
    private String subject;
    private String body;
}
