package com.github.geovanegsfarias.dto;

public record CreateBookRequest(String title, String author, String publisher, Integer totalCopies, Integer availableCopies) {
}