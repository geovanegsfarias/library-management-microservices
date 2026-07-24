package com.github.geovanegsfarias.dto;

public record BookResponse(Long id, String title, String author, String publisher, Integer totalCopies, Integer availableCopies) {
}