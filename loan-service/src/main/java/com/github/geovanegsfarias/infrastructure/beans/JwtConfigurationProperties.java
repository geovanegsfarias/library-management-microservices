package com.github.geovanegsfarias.infrastructure.beans;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtConfigurationProperties(
        RSAPublicKey publicKey,
        RSAPrivateKey privateKey) {
}