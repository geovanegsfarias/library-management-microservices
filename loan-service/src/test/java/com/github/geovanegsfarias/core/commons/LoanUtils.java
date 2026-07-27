package com.github.geovanegsfarias.core.commons;

import com.github.geovanegsfarias.core.entities.Loan;
import com.github.geovanegsfarias.core.enums.LoanStatus;

import java.time.Instant;

public class LoanUtils {

    private UserUtils userUtils;

    public LoanUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }

    public Loan newLoanToSave() {
        var loan = new Loan();
        loan.setId(1L);
        return loan;
    }

    public Loan savedLoan() {
        var savedUser = userUtils.savedUser();

        return new Loan(1L, 1L, "Spring Start Here", savedUser, Instant.parse("2026-07-26T21:40:35.493849Z"), Instant.parse("2026-08-25T21:40:59.595443038Z"), null, LoanStatus.ACTIVE);
    }
}