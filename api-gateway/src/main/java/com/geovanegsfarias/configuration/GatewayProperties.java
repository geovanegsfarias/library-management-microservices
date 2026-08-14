package com.geovanegsfarias.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "gateway.services")
public record GatewayProperties(String bookServiceUri, String loanServiceUri) {
}
