package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.core.exception.LoanNotFoundException;
import com.github.geovanegsfarias.core.gateway.LoanGateway;

public class FindLoanByIdUseCase {

    private final LoanGateway loanGateway;

    public FindLoanByIdUseCase(LoanGateway loanGateway) {
        this.loanGateway = loanGateway;
    }

    public Loan findById(Long id) {
        return loanGateway.findById(id).orElseThrow(() -> new LoanNotFoundException("Loan not found"));
    }
}
