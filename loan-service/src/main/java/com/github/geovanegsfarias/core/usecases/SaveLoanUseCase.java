package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.core.gateway.BookClientGateway;
import com.github.geovanegsfarias.core.gateway.LoanGateway;

public class SaveLoanUseCase {

    private final LoanGateway loanGateway;
    private final BookClientGateway bookClientGateway;
    private final FindUserByEmailUseCase findUserByEmailUseCase;

    public SaveLoanUseCase(LoanGateway loanGateway, BookClientGateway bookClientGateway, FindUserByEmailUseCase findUserByEmailUseCase) {
        this.loanGateway = loanGateway;
        this.bookClientGateway = bookClientGateway;
        this.findUserByEmailUseCase = findUserByEmailUseCase;
    }

    public Loan save(Loan loanToSave, String email) {
        var bookId = loanToSave.getBookId();

        var bookAvailable = bookClientGateway.reserveBook(bookId);

        var user = findUserByEmailUseCase.findByEmail(email);

        loanToSave.initLoan(bookAvailable.title(), user);

        return loanGateway.save(loanToSave);
    }
}
