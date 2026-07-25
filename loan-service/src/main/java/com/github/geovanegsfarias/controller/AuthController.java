package com.github.geovanegsfarias.controller;

import com.github.geovanegsfarias.dto.CreateUserRequest;
import com.github.geovanegsfarias.dto.LoginResponse;
import com.github.geovanegsfarias.dto.UserResponse;
import com.github.geovanegsfarias.mapper.UserMapper;
import com.github.geovanegsfarias.service.AuthService;
import com.github.geovanegsfarias.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper mapper;

    public AuthController(AuthService authService, UserService userService, UserMapper mapper) {
        this.authService = authService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid CreateUserRequest request) {
        log.debug("Request received to register a user");

        var userToSave = mapper.toUser(request);

        var savedUser = userService.save(userToSave);

        var userResponse = mapper.toUserResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(Authentication authentication) {
        log.debug("Request received to authenticate user");

        var token = authService.authenticate(authentication);

        var loginResponse = new LoginResponse(token);

        return ResponseEntity.ok(loginResponse);
    }
}
