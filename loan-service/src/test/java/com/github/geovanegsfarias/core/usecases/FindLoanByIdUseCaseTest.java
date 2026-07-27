package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.LoanUtils;
import com.github.geovanegsfarias.core.commons.UserUtils;
import com.github.geovanegsfarias.core.exception.LoanNotFoundException;
import com.github.geovanegsfarias.core.gateway.LoanGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FindLoanByIdUseCaseTest {
    @Mock
    private LoanGateway loanGateway;
    @InjectMocks
    private FindLoanByIdUseCase findLoanByIdUseCase;
    private final LoanUtils loanUtils = new LoanUtils(new UserUtils());

    @Test
    @DisplayName("findById returns a loan with given id")
    @Order(1)
    void findById_ReturnsLoan_WhenSuccessful() {
        var expectedLoan = loanUtils.savedLoan();

        BDDMockito.when(loanGateway.findById(expectedLoan.getId())).thenReturn(Optional.of(expectedLoan));

        var loan = findLoanByIdUseCase.findById(expectedLoan.getId());

        Assertions.assertThat(expectedLoan).isEqualTo(loan);
    }

    @Test
    @DisplayName("findById throws LoanNotFoundException when loan is not found")
    @Order(2)
    void findById_ThrowsLoanNotFoundException_WhenLoanNotFound() {
        var savedLoan = loanUtils.savedLoan();

        BDDMockito.when(loanGateway.findById(savedLoan.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> findLoanByIdUseCase.findById(savedLoan.getId()))
                .isInstanceOf(LoanNotFoundException.class)
                .withMessage("Loan not found");
    }
}