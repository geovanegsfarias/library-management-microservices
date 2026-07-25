package com.github.geovanegsfarias.dto;

import com.github.geovanegsfarias.model.LoanStatus;

import java.time.Instant;

public record LoanResponse(Long id, Long bookId, String userEmail, Instant loanDate, Instant dueDate, Instant returnedDate, LoanStatus status) {
}