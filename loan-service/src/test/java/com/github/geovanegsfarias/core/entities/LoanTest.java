package com.github.geovanegsfarias.core.entities;

import com.github.geovanegsfarias.core.commons.LoanUtils;
import com.github.geovanegsfarias.core.commons.UserUtils;
import com.github.geovanegsfarias.core.enums.LoanStatus;
import com.github.geovanegsfarias.core.exception.LoanAlreadyReturnedException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoanTest {

    private final LoanUtils loanUtils = new LoanUtils(new UserUtils());

    @Test
    void markOverdue_MarksLoanStatusAsOverdue_WhenSuccessful() {
        var loan = loanUtils.savedLoan();

        loan.markAsOverdue();

        Assertions.assertThat(loan.getStatus()).isEqualTo(LoanStatus.OVERDUE);
    }

    @Test
    void returnLoan_ReturnsLoan_WhenSuccessful() {
        var loan = loanUtils.savedLoan();

        loan.returnLoan();

        Assertions.assertThat(loan.getStatus()).isEqualTo(LoanStatus.RETURNED);
        Assertions.assertThat(loan.getReturnedDate()).isNotNull();
    }

    @Test
    void returnLoan_ThrowsLoanAlreadyReturnedException_WhenLoanStatusIsReturned() {
        var loan = loanUtils.savedLoan();

        loan.setStatus(LoanStatus.RETURNED);

        Assertions.assertThatException()
                .isThrownBy(loan::returnLoan)
                .isInstanceOf(LoanAlreadyReturnedException.class)
                .withMessage("Loan already returned");
    }
}