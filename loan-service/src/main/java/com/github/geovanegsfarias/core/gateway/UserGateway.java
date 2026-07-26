package com.github.geovanegsfarias.core.gateway;

import com.github.geovanegsfarias.core.entities.User;

import java.util.Optional;

public interface UserGateway {

    User save(User user);
    Optional<User> findByEmailIgnoreCase(String email);
    Boolean existsByEmailIgnoreCase(String email);
}