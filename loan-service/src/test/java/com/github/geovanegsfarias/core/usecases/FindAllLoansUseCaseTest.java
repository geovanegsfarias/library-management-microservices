package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.LoanUtils;
import com.github.geovanegsfarias.core.commons.UserUtils;
import com.github.geovanegsfarias.core.gateway.LoanGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FindAllLoansUseCaseTest {
    @Mock
    private LoanGateway loanGateway;
    @InjectMocks
    private FindAllLoansUseCase findAllLoansUseCase;
    private final LoanUtils loanUtils = new LoanUtils(new UserUtils());

    @Test
    @DisplayName("findAll returns a list with all loans")
    @Order(1)
    void findAll_ReturnsAllLoans_WhenSuccessful() {
        var expectedLoanList = Collections.singletonList(loanUtils.savedLoan());

        BDDMockito.when(loanGateway.findAll()).thenReturn(expectedLoanList);

        var bookList = findAllLoansUseCase.findAll();

        Assertions.assertThat(bookList).isNotNull().hasSameElementsAs(expectedLoanList);
    }

}