package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.LoanUtils;
import com.github.geovanegsfarias.core.commons.UserUtils;
import com.github.geovanegsfarias.core.entities.BookAvailable;
import com.github.geovanegsfarias.core.enums.LoanStatus;
import com.github.geovanegsfarias.core.exception.UserNotFoundException;
import com.github.geovanegsfarias.core.gateway.BookClientGateway;
import com.github.geovanegsfarias.core.gateway.LoanGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SaveLoanUseCaseTest {
    @Mock
    private LoanGateway loanGateway;
    @Mock
    private BookClientGateway bookClientGateway;
    @Mock
    private FindUserByEmailUseCase findUserByEmailUseCase;
    @InjectMocks
    private SaveLoanUseCase saveLoanUseCase;
    private final LoanUtils loanUtils = new LoanUtils(new UserUtils());

    @Test
    @DisplayName("saveLoan creates a loan")
    @Order(1)
    void saveLoan_CreatesLoan_WhenSuccessful() {
        var loanToSave = loanUtils.newLoanToSave();
        var expectedSavedLoan = loanUtils.savedLoan();
        var email = expectedSavedLoan.getUser().getEmail();
        var bookAvailable = new BookAvailable(expectedSavedLoan.getBookId(), expectedSavedLoan.getBookTitle());

        BDDMockito.when(bookClientGateway.reserveBook(loanToSave.getBookId())).thenReturn(bookAvailable);
        BDDMockito.when(findUserByEmailUseCase.findByEmail(email)).thenReturn(expectedSavedLoan.getUser());
        BDDMockito.when(loanGateway.save(loanToSave)).thenReturn(expectedSavedLoan);

        var savedLoan = saveLoanUseCase.save(loanToSave, email);

        Assertions.assertThat(loanToSave.getBookTitle()).isEqualTo(bookAvailable.title());
        Assertions.assertThat(loanToSave.getUser()).isEqualTo(expectedSavedLoan.getUser());
        Assertions.assertThat(loanToSave.getStatus()).isEqualTo(LoanStatus.ACTIVE);
        Assertions.assertThat(loanToSave.getDueDate()).isNotNull();
        Assertions.assertThat(savedLoan).isEqualTo(expectedSavedLoan);
    }

    @Test
    @DisplayName("saveLoan throws UserNotFoundException when user is not found")
    @Order(2)
    void saveLoan_ThrowsUserNotFoundException_WhenUserNotFound() {
        var loanToSave = loanUtils.newLoanToSave();
        var email = "usernotfound@gmail.com";
        var bookAvailable = new BookAvailable(loanToSave.getBookId(), "Spring Start Here");

        BDDMockito.when(bookClientGateway.reserveBook(loanToSave.getBookId())).thenReturn(bookAvailable);
        BDDMockito.when(findUserByEmailUseCase.findByEmail(email)).thenThrow(new UserNotFoundException("User not found"));

        Assertions.assertThatException()
                .isThrownBy(() -> saveLoanUseCase.save(loanToSave, email))
                .isInstanceOf(UserNotFoundException.class)
                .withMessage("User not found");
    }
}