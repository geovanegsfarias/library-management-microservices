package com.github.geovanegsfarias.infrastructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(example = "eyJhbOiJIUzsC...") String jwtToken) {
}