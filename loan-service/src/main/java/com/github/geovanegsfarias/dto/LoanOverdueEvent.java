package com.github.geovanegsfarias.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record LoanOverdueEvent(
        @Schema(example = "1") Long loanId,
        @Schema(example = "user@gmail.com") String userEmail,
        @Schema(example = "Spring Start Here") String bookTitle,
        @Schema(example = "2026-07-24T15:00:00.947219575Z") Instant dueDate) {
}