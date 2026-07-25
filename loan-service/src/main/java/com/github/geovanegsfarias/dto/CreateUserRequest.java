package com.github.geovanegsfarias.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank(message = "Email is required") @Email(message = "The email must be valid") String email,
        @NotBlank(message = "Password is required") @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters") String password) {
}