package com.sedeeman.ca.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RabbitMQConfigTest {

    @InjectMocks
    private RabbitMQConfig rabbitMQConfig;

    @Test
    void testFlightStatusExchange() {
        FanoutExchange fanoutExchange = rabbitMQConfig.flightStatusExchange();
        assertNotNull(fanoutExchange);
    }

    @Test
    void testEmailQueue() {
        Queue emailQueue = rabbitMQConfig.emailQueue();
        assertNotNull(emailQueue);
    }

    @Test
    void testBinding() {
        Queue mockEmailQueue = mock(Queue.class);
        FanoutExchange mockFlightStatusExchange = mock(FanoutExchange.class);

        Binding binding = rabbitMQConfig.binding(mockEmailQueue, mockFlightStatusExchange);

        assertNotNull(binding);
        verify(mockEmailQueue).getName();
        verify(mockFlightStatusExchange).getName();
        verifyNoMoreInteractions(mockEmailQueue, mockFlightStatusExchange);
    }
}
