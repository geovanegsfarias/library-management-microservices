package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.core.gateway.BookClientGateway;
import com.github.geovanegsfarias.core.gateway.LoanGateway;

public class ReturnLoanUseCase {

    private final LoanGateway loanGateway;
    private final BookClientGateway bookClientGateway;
    private final FindLoanByIdUseCase findLoanByIdUseCase;

    public ReturnLoanUseCase(LoanGateway loanGateway, BookClientGateway bookClientGateway, FindLoanByIdUseCase findLoanByIdUseCase) {
        this.loanGateway = loanGateway;
        this.bookClientGateway = bookClientGateway;
        this.findLoanByIdUseCase = findLoanByIdUseCase;
    }

    public Loan returnLoan(Long id) {
        var loanToReturn = findLoanByIdUseCase.findById(id);

        bookClientGateway.returnBook(loanToReturn.getBookId());

        loanToReturn.returnLoan();

        return loanGateway.save(loanToReturn);
    }
}