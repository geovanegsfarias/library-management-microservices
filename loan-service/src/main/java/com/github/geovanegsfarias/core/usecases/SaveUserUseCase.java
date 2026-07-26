package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.User;
import com.github.geovanegsfarias.core.exception.UserAlreadyExistsException;
import com.github.geovanegsfarias.core.gateway.PasswordEncoderGateway;
import com.github.geovanegsfarias.core.gateway.UserGateway;

public class SaveUserUseCase {

    private final UserGateway userGateway;
    private final PasswordEncoderGateway passwordEncoderGateway;

    public SaveUserUseCase(UserGateway userGateway, PasswordEncoderGateway passwordEncoderGateway) {
        this.userGateway = userGateway;
        this.passwordEncoderGateway = passwordEncoderGateway;
    }

    public User save(User userToSave) {
        assertEmailIsAvailable(userToSave.getEmail());
        userToSave.setPassword(passwordEncoderGateway.encode(userToSave.getPassword()));
        return userGateway.save(userToSave);
    }

    private void assertEmailIsAvailable(String email) {
        if (userGateway.existsByEmailIgnoreCase(email)) {
            throw new UserAlreadyExistsException("Email already registered");
        }
    }
}