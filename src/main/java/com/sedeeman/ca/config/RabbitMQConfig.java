package com.sedeeman.ca.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public FanoutExchange flightStatusExchange() {
        return new FanoutExchange("flight-status-exchange");
    }

    @Bean
    public Queue emailQueue() {
        return new Queue("email-queue");
    }

    @Bean
    public Binding binding(Queue emailQueue, FanoutExchange flightStatusExchange) {
        return BindingBuilder.bind(emailQueue).to(flightStatusExchange);
    }

}
