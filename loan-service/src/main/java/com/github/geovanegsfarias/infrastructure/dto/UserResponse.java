package com.github.geovanegsfarias.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "user@gmail.com") String email) {
}