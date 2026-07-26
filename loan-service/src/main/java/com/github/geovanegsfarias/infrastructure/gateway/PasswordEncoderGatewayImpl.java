package com.github.geovanegsfarias.infrastructure.gateway;

import com.github.geovanegsfarias.core.gateway.PasswordEncoderGateway;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderGatewayImpl implements PasswordEncoderGateway {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderGatewayImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
