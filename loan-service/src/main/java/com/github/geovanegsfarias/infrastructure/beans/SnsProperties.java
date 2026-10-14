package com.github.geovanegsfarias.infrastructure.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "broker.sns")
public record SnsProperties(String arn) {
}