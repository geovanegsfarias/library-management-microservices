package com.github.geovanegsfarias.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.header")
public record ApiKeyConfigurationProperties(String apiKey) {
}