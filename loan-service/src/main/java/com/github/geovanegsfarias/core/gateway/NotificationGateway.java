package com.github.geovanegsfarias.core.gateway;

import com.github.geovanegsfarias.core.entities.Loan;

public interface NotificationGateway {

    void sendOverdueNotification(Loan loan);
}