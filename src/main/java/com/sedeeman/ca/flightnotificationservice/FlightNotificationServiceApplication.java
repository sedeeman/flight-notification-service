package com.sedeeman.ca.flightnotificationservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class FlightNotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightNotificationServiceApplication.class, args);
	}

}
