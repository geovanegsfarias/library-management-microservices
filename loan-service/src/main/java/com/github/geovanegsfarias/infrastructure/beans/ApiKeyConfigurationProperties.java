package com.github.geovanegsfarias.infrastructure.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.header")
public record ApiKeyConfigurationProperties(String apiKey) {
}