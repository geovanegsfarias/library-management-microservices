package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.LoanUtils;
import com.github.geovanegsfarias.core.commons.UserUtils;
import com.github.geovanegsfarias.core.enums.LoanStatus;
import com.github.geovanegsfarias.core.exception.LoanNotFoundException;
import com.github.geovanegsfarias.core.gateway.BookClientGateway;
import com.github.geovanegsfarias.core.gateway.LoanGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReturnLoanUseCaseTest {
    @Mock
    private LoanGateway loanGateway;
    @Mock
    private BookClientGateway bookClientGateway;
    @Mock
    private FindLoanByIdUseCase findLoanByIdUseCase;
    @InjectMocks
    private ReturnLoanUseCase returnLoanUseCase;
    private final LoanUtils loanUtils = new LoanUtils(new UserUtils());

    @Test
    @DisplayName("returnLoan returns a loan with given id")
    @Order(1)
    void returnLoan_ReturnsLoan_WhenSuccessful() {
        var loan = loanUtils.savedLoan();
        loan.setStatus(LoanStatus.ACTIVE);

        BDDMockito.when(findLoanByIdUseCase.findById(loan.getId())).thenReturn(loan);
        BDDMockito.when(loanGateway.save(Mockito.any())).thenReturn(loan);
        BDDMockito.doNothing().when(bookClientGateway).returnBook(loan.getBookId());

        returnLoanUseCase.returnLoan(loan.getId());

        Assertions.assertThat(loan.getStatus()).isEqualTo(LoanStatus.RETURNED);
        Assertions.assertThat(loan.getReturnedDate()).isNotNull();
        BDDMockito.verify(bookClientGateway, BDDMockito.times(1)).returnBook(loan.getBookId());
        BDDMockito.verify(loanGateway, BDDMockito.times(1)).save(loan);
    }

    @Test
    @DisplayName("returnLoan throws LoanNotFoundException when loan not found")
    @Order(2)
    void returnLoan_ThrowsLoanNotFoundException_WhenLoanNotFound() {
        var loanId = 999L;

        BDDMockito.when(findLoanByIdUseCase.findById(loanId)).thenThrow(new LoanNotFoundException("Loan not found"));

        Assertions.assertThatException()
                .isThrownBy(() -> returnLoanUseCase.returnLoan(loanId))
                .isInstanceOf(LoanNotFoundException.class)
                .withMessage("Loan not found");
    }
}