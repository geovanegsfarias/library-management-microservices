package com.github.geovanegsfarias.infrastructure.gateway;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.core.enums.LoanStatus;
import com.github.geovanegsfarias.core.gateway.LoanGateway;
import com.github.geovanegsfarias.infrastructure.mapper.LoanEntityMapper;
import com.github.geovanegsfarias.infrastructure.persistence.LoanRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class LoanRepositoryGateway implements LoanGateway {

    private final LoanRepository loanRepository;
    private final LoanEntityMapper entityMapper;

    public LoanRepositoryGateway(LoanRepository loanRepository, LoanEntityMapper entityMapper) {
        this.loanRepository = loanRepository;
        this.entityMapper = entityMapper;
    }

    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll().stream().map(entityMapper::toDomain).toList();
    }

    @Override
    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id).map(entityMapper::toDomain);
    }

    @Override
    public Loan save(Loan loan) {
        var entity = entityMapper.toEntity(loan);
        var savedLoanEntity = loanRepository.save(entity);
        return entityMapper.toDomain(savedLoanEntity);
    }

    @Override
    public List<Loan> findByStatusAndDueDateBefore(LoanStatus status, Instant now) {
        return loanRepository.findByStatusAndDueDateBefore(status, now).stream().map(entityMapper::toDomain).toList();
    }
}
