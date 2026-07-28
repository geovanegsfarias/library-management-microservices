package com.github.geovanegsfarias.infrastructure.scheduler;

import com.github.geovanegsfarias.core.usecases.ProcessOverdueLoansUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OverdueLoanScheduler {

    private final ProcessOverdueLoansUseCase processOverdueLoansUseCase;

    public OverdueLoanScheduler(ProcessOverdueLoansUseCase processOverdueLoansUseCase) {
        this.processOverdueLoansUseCase = processOverdueLoansUseCase;
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void run() {
        processOverdueLoansUseCase.markOverdue();
    }
}