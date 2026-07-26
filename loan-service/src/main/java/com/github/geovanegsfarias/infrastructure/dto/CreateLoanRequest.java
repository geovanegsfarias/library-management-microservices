package com.github.geovanegsfarias.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

public record CreateLoanRequest(
        @NotNull(message = "Book ID is required") Long bookId) {
}