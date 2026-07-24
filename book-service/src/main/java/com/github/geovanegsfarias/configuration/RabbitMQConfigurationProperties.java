package com.github.geovanegsfarias.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "broker")
public record RabbitMQConfigurationProperties(String exchangeName, String bookQueue) {
}