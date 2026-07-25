package com.github.geovanegsfarias.repository;

import com.github.geovanegsfarias.model.Loan;
import com.github.geovanegsfarias.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatusAndDueDateBefore(LoanStatus status, Instant now);
}