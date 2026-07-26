package com.github.geovanegsfarias.infrastructure.persistence;

import com.github.geovanegsfarias.core.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    List<LoanEntity> findByStatusAndDueDateBefore(LoanStatus status, Instant now);
}