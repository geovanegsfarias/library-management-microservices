package com.github.geovanegsfarias.infrastructure.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "broker")
public record RabbitMQConfigurationProperties(String exchangeName, String notificationQueue) {
}