package com.github.geovanegsfarias.infrastructure.dto;

import com.github.geovanegsfarias.core.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

public record LoanResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "1") Long bookId,
        @Schema(example = "user@gmail.com") String userEmail,
        @Schema(example = "2026-07-25T15:00:00.947219575Z") Instant loanDate,
        @Schema(example = "2026-08-24T15:00:00.947219575Z") Instant dueDate,
        @Schema(example = "null") Instant returnedDate,
        @Schema(example = "ACTIVE") LoanStatus status) {
}