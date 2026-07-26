package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.core.gateway.LoanGateway;

import java.util.List;

public class FindAllLoansUseCase {

    private final LoanGateway loanGateway;

    public FindAllLoansUseCase(LoanGateway loanGateway) {
        this.loanGateway = loanGateway;
    }

    public List<Loan> findAll() {
        return loanGateway.findAll();
    }
}