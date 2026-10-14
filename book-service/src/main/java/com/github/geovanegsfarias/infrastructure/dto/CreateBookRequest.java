package com.github.geovanegsfarias.infrastructure.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateBookRequest(
        @NotBlank(message = "Title is required") String title,
        @NotBlank(message = "Author name is required") String author,
        @NotBlank(message = "Publisher name is required") String publisher,
        @NotNull(message = "Total copies are required") @Min(value = 1, message = "Total copies must be at least 1") Integer totalCopies,
        @NotNull(message = "Available copies are required") @PositiveOrZero(message = "Available copies must be zero or greater") Integer availableCopies) {
}
