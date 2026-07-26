package com.github.geovanegsfarias.core.gateway;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.core.enums.LoanStatus;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface LoanGateway {

    List<Loan> findAll();
    Optional<Loan> findById(Long id);
    Loan save(Loan loan);
    List<Loan> findByStatusAndDueDateBefore(LoanStatus status, Instant now);
}