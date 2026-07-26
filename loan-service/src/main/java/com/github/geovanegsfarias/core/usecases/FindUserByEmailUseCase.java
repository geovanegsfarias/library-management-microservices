package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.User;
import com.github.geovanegsfarias.core.exception.UserNotFoundException;
import com.github.geovanegsfarias.core.gateway.UserGateway;

public class FindUserByEmailUseCase {

    private final UserGateway userGateway;

    public FindUserByEmailUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User findByEmail(String email) {
        return userGateway.findByEmailIgnoreCase(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
