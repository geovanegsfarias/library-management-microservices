package com.github.geovanegsfarias.dto;

import java.time.Instant;

public record LoanOverdueEvent(Long loanId, String userEmail, String bookTitle, Instant dueDate) {
}