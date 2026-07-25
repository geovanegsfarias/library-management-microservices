package com.github.geovanegsfarias.controller;

import com.github.geovanegsfarias.dto.CreateUserRequest;
import com.github.geovanegsfarias.dto.LoginResponse;
import com.github.geovanegsfarias.dto.UserResponse;
import com.github.geovanegsfarias.mapper.UserMapper;
import com.github.geovanegsfarias.service.AuthService;
import com.github.geovanegsfarias.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Authentication")
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
    @Operation(summary = "Register user", security = {})
    @ApiResponse(responseCode = "201", description = "User created")
    @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "409", description = "Email already registered", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<UserResponse> register(@RequestBody @Valid CreateUserRequest request) {
        log.debug("Request received to register a user");

        var userToSave = mapper.toUser(request);

        var savedUser = userService.save(userToSave);

        var userResponse = mapper.toUserResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Log in", description = "Authenticate and return a JWT token", security = @SecurityRequirement(name = "Basic Auth"))
    @ApiResponse(responseCode = "200", description = "Authenticated")
    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = ProblemDetail.class)))
    public ResponseEntity<LoginResponse> login(Authentication authentication) {
        log.debug("Request received to authenticate user");

        var token = authService.authenticate(authentication);

        var loginResponse = new LoginResponse(token);

        return ResponseEntity.ok(loginResponse);
    }
}
