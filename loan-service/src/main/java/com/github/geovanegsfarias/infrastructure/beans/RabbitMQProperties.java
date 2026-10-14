package com.github.geovanegsfarias.infrastructure.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "broker.rabbitmq")
public record RabbitMQProperties(String exchangeName, String notificationQueue) {
}