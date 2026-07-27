package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.commons.LoanUtils;
import com.github.geovanegsfarias.core.commons.UserUtils;
import com.github.geovanegsfarias.core.enums.LoanStatus;
import com.github.geovanegsfarias.core.gateway.LoanGateway;
import com.github.geovanegsfarias.core.gateway.NotificationGateway;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessOverdueLoansUseCaseTest {
    @Mock
    private LoanGateway loanGateway;
    @Mock
    private NotificationGateway notificationGateway;
    @InjectMocks
    private ProcessOverdueLoansUseCase processOverdueLoansUseCase;
    private final LoanUtils loanUtils = new LoanUtils(new UserUtils());

    @Test
    @DisplayName("markOverdue processes overdue loans successfully")
    @Order(1)
    void markOverdue_ProcessesOverdueLoans_WhenSuccessful() {
        var overdueLoan = loanUtils.savedLoan();
        overdueLoan.setStatus(LoanStatus.ACTIVE);
        overdueLoan.setDueDate(Instant.now().minusSeconds(86400));

        BDDMockito.when(loanGateway.findByStatusAndDueDateBefore(Mockito.eq(LoanStatus.ACTIVE), Mockito.any(Instant.now().getClass()))).thenReturn(List.of(overdueLoan));
        BDDMockito.when(loanGateway.save(Mockito.any())).thenReturn(overdueLoan);

        processOverdueLoansUseCase.markOverdue();

        Assertions.assertThat(overdueLoan.getStatus()).isEqualTo(LoanStatus.OVERDUE);
        BDDMockito.verify(notificationGateway, BDDMockito.times(1)).sendOverdueNotification(Mockito.any());
        BDDMockito.verify(loanGateway, BDDMockito.times(1)).save(overdueLoan);
    }
}