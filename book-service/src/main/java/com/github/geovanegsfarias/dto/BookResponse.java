package com.github.geovanegsfarias.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record BookResponse(
        @Schema(example = "1") Long id,
        @Schema(example = "Spring Start Here") String title,
        @Schema(example = "Laurentiu Spilca") String author,
        @Schema(example = "Manning") String publisher,
        @Schema(example = "10") Integer totalCopies,
        @Schema(example = "10") Integer availableCopies) {
}