package com.github.geovanegsfarias.service;

import com.github.geovanegsfarias.exception.UserAlreadyExistsException;
import com.github.geovanegsfarias.model.User;
import com.github.geovanegsfarias.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User findByEmailOrThrowException(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User save(User userToSave) {
        assertEmailIsAvailable(userToSave.getEmail());
        userToSave.setPassword(encoder.encode(userToSave.getPassword()));
        return userRepository.save(userToSave);
    }

    private void assertEmailIsAvailable(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new UserAlreadyExistsException("Email already registered");
        }
    }
}
