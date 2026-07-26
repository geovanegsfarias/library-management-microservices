package com.github.geovanegsfarias.core.usecases;

import com.github.geovanegsfarias.core.enums.LoanStatus;
import com.github.geovanegsfarias.core.gateway.LoanGateway;
import com.github.geovanegsfarias.core.gateway.NotificationGateway;

import java.time.Instant;

public class ProcessOverdueLoansUseCase {

    private final LoanGateway loanGateway;
    private final NotificationGateway notificationGateway;

    public ProcessOverdueLoansUseCase(LoanGateway loanGateway, NotificationGateway notificationGateway) {
        this.loanGateway = loanGateway;
        this.notificationGateway = notificationGateway;
    }

    public void markOverdue() {
        var loanList = loanGateway.findByStatusAndDueDateBefore(LoanStatus.ACTIVE, Instant.now());

        loanList.forEach(loan -> {
            loan.markAsOverdue();
            loanGateway.save(loan);
            notificationGateway.sendOverdueNotification(loan);
        });
    }
}